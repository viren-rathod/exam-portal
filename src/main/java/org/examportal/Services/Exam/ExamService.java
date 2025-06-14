package org.examportal.Services.Exam;

import org.examportal.Constants.Status;
import org.examportal.DTOs.Exam.ExamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ExamService {
    ExamDto addExam(ExamDto exam, String user);

    ExamDto updateExam(ExamDto exam, String user);

    ExamDto startExam(Long examId, String user);

    ExamDto stopExam(Long examId, String user);

    Set<ExamDto> getExams();

    Page<ExamDto> findPaginated(Pageable pageable, String searchData, Status status);

    Page<ExamDto> getAllActiveExams(Pageable pageable, String searchData, Long userId);

    ExamDto getExam(Long examId);

    void deleteExam(Long examId);
}
