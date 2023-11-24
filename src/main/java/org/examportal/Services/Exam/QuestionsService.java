package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.DTOs.Exam.QuestionsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface QuestionsService {
    QuestionsDto addQuestion(QuestionsDto question, String user);

    QuestionsDto updateQuestions(QuestionsDto question, String user);

    Set<QuestionsDto> findAllQuestions();

    Page<QuestionsDto> findPaginated(Pageable pageable, String searchData);

    QuestionsDto findByQuestionId(Long questionId);

    Set<QuestionsDto> findQuestionsByCategoryId(Long categoryId);

    void deleteQuestion(Long questionId);

    OptionDto findAnswerByQuestionId(Long questionId);
}
