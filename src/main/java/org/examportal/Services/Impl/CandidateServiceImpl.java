package org.examportal.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Status;
import org.examportal.DTOs.CandidateDto;
import org.examportal.DTOs.CollegeDto;
import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.Models.Candidate;
import org.examportal.Models.College;
import org.examportal.Models.Exam.Exam;
import org.examportal.Models.User;
import org.examportal.Repositories.CandidateRepository;
import org.examportal.Services.CandidateService;
import org.examportal.Services.CollegeService;
import org.examportal.Services.Exam.ExamService;
import org.examportal.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserService userService;
    private final ExamService examService;
    private final CollegeService collegeService;
    private final ModelMapper modelMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository, UserService userService, ExamService examService, CollegeService collegeService, ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.userService = userService;
        this.examService = examService;
        this.collegeService = collegeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Candidate create(CandidateDto candidateDto, String user) {
        log.info(String.format("create - start %s", candidateDto));
        User newUser = userService.findByEmail(candidateDto.getEmail());
        ExamDto examDto = examService.getExam(candidateDto.getExamId());
        Exam exam = modelMapper.map(examDto, Exam.class);
        CollegeDto collegeDto = collegeService.findById(candidateDto.getCollegeId());
        College college = modelMapper.map(collegeDto, College.class);

        Candidate candidate = new Candidate();
        candidate.setUser(newUser);
        candidate.setExam(exam);
        candidate.setEmail(candidateDto.getEmail());
        candidate.setCollege(college);
        candidate.setEnrollmentNumber(candidateDto.getEnrollmentNumber());
        candidate.setContactNumber(candidateDto.getContactNumber());
        candidate.setSscPercentage(candidateDto.getSscPercentage());
        candidate.setHscPercentage(candidateDto.getHscPercentage());
        candidate.setCgpa(candidateDto.getCgpa());
        candidate.setCandidateStatus(Status.INACTIVE);
        candidate.setExamStartTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        candidate.setExamEndTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime().plusHours(exam.getExamTime()));

        candidateRepository.save(candidate);
        log.info(String.format("create - end %s", candidate));
        return candidate;
    }

    @Override
    public Set<CandidateDto> findAll() {
        log.info("findAll - start ");
        List<Candidate> candidates = candidateRepository.findAll();
        log.info(String.format("findAll - end %s", candidates));
        return candidates.stream().map(candidate -> modelMapper.map(candidate, CandidateDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Page<CandidateDto> findPaginated(Pageable pageable) {
        log.info(String.format("findPaginated - start %s", pageable));
        Page<Candidate> page = candidateRepository.findAll(pageable);
        log.info(String.format("findPaginated - end %s", page));
        return page.map(candidate -> modelMapper.map(candidate, CandidateDto.class));
    }
}
