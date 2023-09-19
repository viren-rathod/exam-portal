package org.examportal.Services;

import org.examportal.DTOs.ExamDto;
import org.examportal.Models.Exam;

import java.util.Set;

public interface ExamService {
    ExamDto addExam(ExamDto exam);

    ExamDto updateExam(ExamDto exam);

    Set<ExamDto> getExams();

    ExamDto getExam(Long examId);

    void deleteExam(Long examId);
}
