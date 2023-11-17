package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Status;
import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Exam;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.examportal.Repositories.Exam.ExamRepository;
import org.examportal.Services.Exam.ExamService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ExamServiceImpl(ExamRepository examRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.examRepository = examRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ExamDto addExam(ExamDto exam, String user) {
        log.info(String.format("addExam - start %s", exam));
        Exam savedExam = modelMapper.map(exam, Exam.class);
        savedExam.update(user);
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
    public ExamDto startExam(Long examId,String user) {
        log.info(String.format("startExam - start %d", examId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
        exam.setStatus(Status.ACTIVE);
        examRepository.save(exam);
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("startExam - end %s", examDto));
        return examDto;
    }

    @Override
    public ExamDto stopExam(Long examId,String user) {
        log.info(String.format("stopExam - start %d", examId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
        exam.setStatus(Status.INACTIVE);
        examRepository.save(exam);
        ExamDto examDto = modelMapper.map(exam, ExamDto.class);
        log.info(String.format("startExam - end %s", examDto));
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
    public Page<ExamDto> findPaginated(Pageable pageable, String searchData) {
        log.info(String.format("findPaginated - start %s %s", pageable, searchData));
        Page<Exam> page = examRepository.findAllWithFilters(searchData, pageable);
        log.info(String.format("findPaginated - end %s", page));
        return page.map(exam -> modelMapper.map(exam, ExamDto.class));
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
