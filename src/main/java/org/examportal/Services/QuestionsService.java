package org.examportal.Services;

import org.examportal.Models.Category;
import org.examportal.Models.Questions;

import java.util.Set;

public interface QuestionsService {
    Questions addQuestion(Questions question);

    Questions updateQuestions(Questions question);

    Set<Questions> getQuestions();

    Questions getQuestion(Long questionId);

    Set<Questions> getQuestionsOfCategory(Category category);

    void deleteQuestion(Long questionId);
}
