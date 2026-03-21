package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.ExamStatus;
import org.examportal.Constants.Exam.ExamMessages;
import org.examportal.Constants.Status;
import org.examportal.DTOs.Exam.*;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.BaseEntity;
import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.*;
import org.examportal.Models.User;
import org.examportal.Repositories.CandidateRepository;
import org.examportal.Repositories.Exam.ExamQuestionRepository;
import org.examportal.Repositories.Exam.ExamRepository;
import org.examportal.Repositories.Exam.QuestionsRepository;
import org.examportal.Repositories.UserRepository;
import org.examportal.Services.Exam.StudentExamService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentExamServiceImpl implements StudentExamService {

    private final ExamRepository examRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final ModelMapper modelMapper;

    public StudentExamServiceImpl(ExamRepository examRepository,
                                  CandidateRepository candidateRepository,
                                  UserRepository userRepository,
                                  QuestionsRepository questionsRepository,
                                  ExamQuestionRepository examQuestionRepository,
                                  ModelMapper modelMapper) {
        this.examRepository = examRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.questionsRepository = questionsRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ExamDto> getActiveExams(Pageable pageable, String searchData, Long userId) {
        log.info("getActiveExams - start");
        Page<Exam> page = examRepository.findAllWithFilters(searchData, Status.ACTIVE, pageable);
        return page.map(exam -> {
            ExamDto examDto = modelMapper.map(exam, ExamDto.class);
            Long candidateCount = candidateRepository.countByExamId(exam.getId());
            examDto.setCandidateCount(candidateCount);
            if (userId != null) {
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    Optional<Candidate> optional = candidateRepository.findByUserAndExam(user.get(), exam);
                    optional.ifPresent(candidate -> examDto.setCandidateStatus(candidate.getCandidateStatus()));
                }
            }
            return examDto;
        });
    }

    @Override
    @Transactional
    public List<StudentExamQuestionDto> startExam(Long examId, String username) {
        log.info("startExam - start examId={} username={}", examId, username);

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));

        // Validate exam is active
        if (exam.getStatus() != Status.ACTIVE) {
            throw new RuntimeException(ExamMessages.EXAM_NOT_ACTIVE);
        }

        // Check if user already has a candidate entry for this exam
        Optional<Candidate> existingCandidate = candidateRepository.findByUserAndExam(user, exam);

        if (existingCandidate.isPresent()) {
            Candidate candidate = existingCandidate.get();

            // If already attended, throw error
            if (candidate.getCandidateStatus() == ExamStatus.ATTENDED) {
                throw new RuntimeException(ExamMessages.EXAM_ALREADY_ATTENDED);
            }

            // If already attending, return existing questions (idempotent)
            if (candidate.getCandidateStatus() == ExamStatus.ATTENDING) {
                log.info("startExam - already attending, returning existing questions");
                return getQuestionsForCandidate(candidate);
            }
        } else {
            // Candidate has not filled out the registration form
            throw new RuntimeException("You must fill out the candidate registration form for this exam first!");
        }

        Candidate candidate = existingCandidate.get();
        candidate.setCandidateStatus(ExamStatus.ATTENDING);
        candidate.setExamStartTime(LocalDateTime.now());
        candidate.setExamEndTime(LocalDateTime.now().plusMinutes(exam.getExamTime()));
        candidate.update(username);
        candidateRepository.save(candidate);

        // Generate random questions
        int totalQuestionsNeeded = Integer.parseInt(exam.getTotalQuestions());
        List<Long> categoryIds = exam.getCategories().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        List<Long> randomQuestionIds = questionsRepository.findRandomQuestionIdsByCategoryIds(
                categoryIds, totalQuestionsNeeded);

        List<Questions> randomQuestions = new java.util.ArrayList<>(questionsRepository.findAllById(randomQuestionIds));
        java.util.Collections.shuffle(randomQuestions);

        if (randomQuestions.size() < totalQuestionsNeeded) {
            log.warn("startExam - not enough questions available. Needed={}, Found={}",
                    totalQuestionsNeeded, randomQuestions.size());
            throw new RuntimeException(ExamMessages.NOT_ENOUGH_QUESTIONS);
        }

        // Save exam questions
        for (Questions question : randomQuestions) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setCandidate(candidate);
            examQuestion.setQuestion(question);
            examQuestion.update(username);
            examQuestionRepository.save(examQuestion);
        }

        log.info("startExam - end, generated {} questions", randomQuestions.size());
        return getQuestionsForCandidate(candidate);
    }

    @Override
    @Transactional
    public StudentExamQuestionDto submitAnswer(SubmitAnswerDto dto, String username) {
        log.info("submitAnswer - start examQuestionId={}", dto.getExamQuestionId());

        ExamQuestion examQuestion = examQuestionRepository.findById(dto.getExamQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("ExamQuestion", "id", dto.getExamQuestionId()));

        // Validate that this question belongs to the user
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        if (!examQuestion.getCandidate().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to answer this question!");
        }

        // Validate candidate is still ATTENDING
        if (examQuestion.getCandidate().getCandidateStatus() != ExamStatus.ATTENDING) {
            throw new RuntimeException(ExamMessages.EXAM_ALREADY_ATTENDED);
        }

        // Set selected option
        if (dto.getSelectedOptionId() != null) {
            Options selectedOption = examQuestion.getQuestion().getOptions().stream()
                    .filter(opt -> opt.getId().equals(dto.getSelectedOptionId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Option", "id", dto.getSelectedOptionId()));
            examQuestion.setSelectedOption(selectedOption);
        } else {
            examQuestion.setSelectedOption(null);
        }

        examQuestionRepository.save(examQuestion);
        log.info("submitAnswer - end");
        return mapToStudentExamQuestionDto(examQuestion);
    }

    @Override
    public List<StudentExamQuestionDto> getExamQuestions(Long examId, String username) {
        log.info("getExamQuestions - start examId={} username={}", examId, username);

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));

        Candidate candidate = candidateRepository.findByUserAndExam(user, exam)
                .orElseThrow(() -> new RuntimeException(ExamMessages.EXAM_NOT_STARTED));

        return getQuestionsForCandidate(candidate);
    }

    @Override
    @Transactional
    public ExamResultDto finishExam(Long examId, String username) {
        log.info("finishExam - start examId={} username={}", examId, username);

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));

        Candidate candidate = candidateRepository.findByUserAndExam(user, exam)
                .orElseThrow(() -> new RuntimeException(ExamMessages.EXAM_NOT_STARTED));

        if (candidate.getCandidateStatus() == ExamStatus.ATTENDED) {
            throw new RuntimeException(ExamMessages.EXAM_ALREADY_ATTENDED);
        }

        // Evaluate all answers
        List<ExamQuestion> examQuestions = examQuestionRepository.findByCandidate(candidate);
        int correctCount = 0;

        for (ExamQuestion eq : examQuestions) {
            if (eq.getSelectedOption() != null && eq.getQuestion().getAnswer() != null) {
                boolean isCorrect = eq.getSelectedOption().getId().equals(eq.getQuestion().getAnswer().getId());
                eq.setIsCorrect(isCorrect);
                if (isCorrect) {
                    correctCount++;
                }
            } else {
                eq.setIsCorrect(false);
            }
            examQuestionRepository.save(eq);
        }

        // Update candidate
        candidate.setCandidateStatus(ExamStatus.ATTENDED);
        candidate.setExamEndTime(LocalDateTime.now());
        candidate.setScore(correctCount);
        candidate.setTotalQuestions(examQuestions.size());
        candidateRepository.save(candidate);

        log.info("finishExam - end, score={}/{}", correctCount, examQuestions.size());
        return buildExamResultDto(exam, candidate, correctCount, examQuestions.size());
    }

    @Override
    public ExamResultDto getExamResult(Long examId, String username) {
        log.info("getExamResult - start examId={} username={}", examId, username);

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));

        Candidate candidate = candidateRepository.findByUserAndExam(user, exam)
                .orElseThrow(() -> new RuntimeException(ExamMessages.EXAM_NOT_STARTED));

        if (candidate.getCandidateStatus() != ExamStatus.ATTENDED) {
            throw new RuntimeException(ExamMessages.EXAM_NOT_STARTED);
        }

        return buildExamResultDto(exam, candidate, candidate.getScore(),
                candidate.getTotalQuestions());
    }

    // ---- Private helper methods ----

    private List<StudentExamQuestionDto> getQuestionsForCandidate(Candidate candidate) {
        List<ExamQuestion> examQuestions = examQuestionRepository.findByCandidate(candidate);
        return examQuestions.stream()
                .map(this::mapToStudentExamQuestionDto)
                .collect(Collectors.toList());
    }

    private StudentExamQuestionDto mapToStudentExamQuestionDto(ExamQuestion eq) {
        StudentExamQuestionDto dto = new StudentExamQuestionDto();
        dto.setExamQuestionId(eq.getId());
        dto.setQuestionId(eq.getQuestion().getId());
        dto.setTitle(eq.getQuestion().getTitle());
        dto.setDescription(eq.getQuestion().getDescription());

        // Map options (without revealing the answer)
        if (eq.getQuestion().getOptions() != null) {
            List<OptionDto> options = eq.getQuestion().getOptions().stream()
                    .map(opt -> {
                        OptionDto optDto = new OptionDto();
                        optDto.setId(opt.getId());
                        optDto.setTitle(opt.getTitle());
                        return optDto;
                    })
                    .collect(Collectors.toList());
            dto.setOptions(options);
        }

        // Set selected option if already answered
        if (eq.getSelectedOption() != null) {
            dto.setSelectedOptionId(eq.getSelectedOption().getId());
        }

        return dto;
    }

    private ExamResultDto buildExamResultDto(Exam exam, Candidate candidate,
                                             int correctCount, int totalQuestions) {
        ExamResultDto result = new ExamResultDto();
        result.setExamId(exam.getId());
        result.setExamTitle(exam.getTitle());
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctCount);
        result.setTotalMarks(totalQuestions); // each question = 1 mark
        result.setScoredMarks(correctCount); // each question = 1 mark
        return result;
    }
}
