package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.ExamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ExamService {
    ExamDto addExam(ExamDto exam,String user);

    ExamDto updateExam(ExamDto exam,String user);

    Set<ExamDto> getExams();

    Page<ExamDto> findPaginated(Pageable pageable);

    ExamDto getExam(Long examId);

    void deleteExam(Long examId);
}
