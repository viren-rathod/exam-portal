package org.examportal.Services;

import org.examportal.DTOs.QuestionsDto;

import java.util.Set;

public interface QuestionsService {
    QuestionsDto addQuestion(QuestionsDto question, String user);

    QuestionsDto updateQuestions(QuestionsDto question, String user);

    Set<QuestionsDto> findAllQuestions();

    QuestionsDto findByQuestionId(Long questionId);

    Set<QuestionsDto> findQuestionsByCategoryId(Long categoryId);

    void deleteQuestion(Long questionId);
}
