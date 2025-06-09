package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Status;
import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Helper.CommonHelper;
import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.Exam;
import org.examportal.Models.User;
import org.examportal.Repositories.CandidateRepository;
import org.examportal.Repositories.Exam.ExamRepository;
import org.examportal.Repositories.UserRepository;
import org.examportal.Services.Exam.ExamService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ExamServiceImpl(ExamRepository examRepository, CandidateRepository candidateRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.examRepository = examRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ExamDto addExam(ExamDto exam, String user) {
        log.info(String.format("addExam - start %s", exam));
        Exam savedExam = modelMapper.map(exam, Exam.class);
        String examCode = CommonHelper.generateRandomCode();
        savedExam.update(user);
        savedExam.setExamCode(examCode);
        examRepository.save(savedExam);
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("addExam - start %s", exam));
        return examDto;
    }

    @Override
    public ExamDto updateExam(ExamDto exam, String user) {
        log.info(String.format("updateExam - start %s", exam));
        Exam savedExam = modelMapper.map(exam, Exam.class);
        savedExam.update(user);
        examRepository.save(savedExam);
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("updateExam - end %s", savedExam));
        return examDto;
    }

    @Override
    public ExamDto startExam(Long examId, String user) {
        log.info(String.format("startExam - start %d", examId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
        exam.setStatus(Status.ACTIVE);
        examRepository.save(exam);
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("startExam - end %s", examDto));
        return examDto;
    }

    @Override
    public ExamDto stopExam(Long examId, String user) {
        log.info(String.format("stopExam - start %d", examId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
        exam.setStatus(Status.INACTIVE);
        examRepository.save(exam);
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("stopExam - end %s", examDto));
        return examDto;
    }

    @Override
    public Set<ExamDto> getExams() {
        log.info("getExams - start ");
        Set<Exam> exams = new LinkedHashSet<>(examRepository.findAll());
        log.info(String.format("getExams - end %s", exams));
        return exams.stream().map(exam -> modelMapper.map(exam, ExamDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Page<ExamDto> findPaginated(Pageable pageable, String searchData, Status status) {
        log.info(String.format("findPaginated - start %s %s %s", pageable, searchData, status));
        Page<Exam> page = examRepository.findAllWithFilters(searchData, status, pageable);
        log.info(String.format("findPaginated - end %s", page));
        return page.map(exam -> modelMapper.map(exam, ExamDto.class));
    }

    @Override
    public Page<ExamDto> getAllActiveExams(Pageable pageable, String searchData, Long userId) {
        log.info(String.format("getAllActiveExams - start %s %s %d", pageable, searchData, userId));
        Page<Exam> page = examRepository.findAllWithFilters(searchData, Status.ACTIVE, pageable);
        log.info(String.format("getAllActiveExams - end %s", page));
        return page.map(exam -> {
            ExamDto examDto = modelMapper.map(exam, ExamDto.class);
            Long candidateCount = candidateRepository.countByExamId(exam.getId());
            examDto.setCandidateCount(candidateCount);
            if (userId != null) {
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    Optional<Candidate> optional = candidateRepository.findByUser(user.get());
                    if (optional.isPresent()) {
                        Candidate candidate = optional.get();
                        examDto.setCandidateStatus(candidate.getCandidateStatus());
                    }
                }
            }
            return examDto;
        });
    }

    @Override
    public ExamDto getExam(Long examId) {
        log.info(String.format("getExam - start %d", examId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("getExam - end %s", examDto));
        return examDto;
    }

    @Override
    public void deleteExam(Long examId) {
        log.info(String.format("deleteExam - start %d", examId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
        log.info(String.format("deleteExam - end %s", exam));
        examRepository.delete(exam);
    }
}
