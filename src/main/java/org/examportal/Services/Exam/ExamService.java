package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.ExamDto;

import java.util.Set;

public interface ExamService {
    ExamDto addExam(ExamDto exam);

    ExamDto updateExam(ExamDto exam);

    Set<ExamDto> getExams();

    ExamDto getExam(Long examId);

    void deleteExam(Long examId);
}
