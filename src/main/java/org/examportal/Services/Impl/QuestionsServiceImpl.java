package org.examportal.Services.Impl;

import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Questions;
import org.examportal.Repositories.QuestionsRepository;
import org.examportal.Services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class QuestionsServiceImpl implements QuestionsService {
    @Autowired
    private QuestionsRepository questionsRepository;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    @Override
    public Questions addQuestion(Questions question) {
        return questionsRepository.save(question);
    }

    @Override
    public Questions updateQuestions(Questions question) {
        return questionsRepository.save(question);
    }

    @Override
    public Set<Questions> getQuestions() {
        return new LinkedHashSet<>(questionsRepository.findAll());
    }

    @Override
    public Questions getQuestion(Long questionId) {
        return questionsRepository.findById(questionId).get();
    }

    @Override
    public Set<Questions> getQuestionsOfCategory(Category category) {
        return questionsRepository.findByCategory(category);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionsRepository.deleteById(questionId);
    }
}
