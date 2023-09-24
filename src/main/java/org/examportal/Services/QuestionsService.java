package org.examportal.Services;

import org.examportal.DTOs.QuestionsDto;
import org.examportal.Models.Exam.Questions;

import java.util.Set;

public interface QuestionsService {
    Questions addQuestion(QuestionsDto question, String user);

    Questions updateQuestions(QuestionsDto question, String user);

    Set<Questions> findAllQuestions();

    Questions findByQuestionId(Long questionId);

    Set<Questions> findQuestionsByCategoryId(Long categoryId);

    void deleteQuestion(Long questionId);
}
