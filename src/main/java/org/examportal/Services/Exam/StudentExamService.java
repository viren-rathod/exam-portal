package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.DTOs.Exam.ExamResultDto;
import org.examportal.DTOs.Exam.StudentExamQuestionDto;
import org.examportal.DTOs.Exam.SubmitAnswerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentExamService {

    Page<ExamDto> getActiveExams(Pageable pageable, String searchData, Long userId);

    List<StudentExamQuestionDto> startExam(Long examId, String username);

    StudentExamQuestionDto submitAnswer(SubmitAnswerDto dto, String username);

    List<StudentExamQuestionDto> getExamQuestions(Long examId, String username);

    ExamResultDto finishExam(Long examId, String username);

    ExamResultDto getExamResult(Long examId, String username);
}
