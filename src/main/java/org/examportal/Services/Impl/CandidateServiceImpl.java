package org.examportal.Services.Impl;

import org.examportal.DTOs.CandidateDto;
import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.Exam;
import org.examportal.Models.User;
import org.examportal.Repositories.CandidateRepository;
import org.examportal.Services.CandidateService;
import org.examportal.Services.Exam.ExamService;
import org.examportal.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserService userService;
    private final ExamService examService;
    private final ModelMapper modelMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository, UserService userService, ExamService examService, ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.userService = userService;
        this.examService = examService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Candidate create(CandidateDto candidateDto, String user) {
        User newUser = userService.findByEmail(candidateDto.getUsername());
        ExamDto examDto = examService.getExam(candidateDto.getExamId());
        Exam exam = modelMapper.map(examDto, Exam.class);

        Candidate candidate = new Candidate();
        candidate.setUser(newUser);
        candidate.setExam(exam);
        candidate.setEmail(candidateDto.getEmail());
        candidate.setCollegeId(candidateDto.getCollegeId());
        candidate.setEnrollmentNumber(candidateDto.getEnrollmentNumber());
        candidate.setContactNumber(candidateDto.getContactNumber());
        candidate.setSscPercentage(candidateDto.getSscPercentage());
        candidate.setHscPercentage(candidateDto.getHscPercentage());
        candidate.setCgpa(candidateDto.getCgpa());
        candidate.setExamStartTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        candidate.setExamEndTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime().plusHours(exam.getExamTime()));

        candidateRepository.save(candidate);
        return candidate;
    }
}
