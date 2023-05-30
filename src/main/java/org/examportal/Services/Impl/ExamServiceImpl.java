package org.examportal.Services.Impl;

import org.examportal.DTOs.ExamDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam;
import org.examportal.Repositories.ExamRepository;
import org.examportal.Services.ExamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;
    private ModelMapper modelMapper;

    public ExamServiceImpl(ExamRepository examRepository, ModelMapper modelMapper) {
        this.examRepository = examRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ExamDto addExam(ExamDto exam) {
        Exam savedExam = modelMapper.map(exam,Exam.class);
        examRepository.save(savedExam);
        return modelMapper.map(savedExam,ExamDto.class);
    }

    @Override
    public ExamDto updateExam(ExamDto exam) {
        Exam savedExam = modelMapper.map(exam,Exam.class);
        examRepository.save(savedExam);
        return modelMapper.map(savedExam,ExamDto.class);
    }

    @Override
    public Set<ExamDto> getExams() {
        Set<Exam> exams = new LinkedHashSet<>(examRepository.findAll());
        return exams.stream().map(exam -> modelMapper.map(exam, ExamDto.class)).collect(Collectors.toSet());
    }

    @Override
    public ExamDto getExam(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(()->new ResourceNotFoundException("Exam","id",examId));
        return modelMapper.map(exam, ExamDto.class);
    }

    @Override
    public void deleteExam(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(()->new ResourceNotFoundException("Exam","id",examId));
        examRepository.delete(exam);
    }
}
