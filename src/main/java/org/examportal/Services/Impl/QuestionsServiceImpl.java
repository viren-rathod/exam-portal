package org.examportal.Services.Impl;

import org.examportal.DTOs.QuestionsDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Questions;
import org.examportal.Repositories.CategoryRepository;
import org.examportal.Repositories.QuestionsRepository;
import org.examportal.Services.QuestionsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class QuestionsServiceImpl implements QuestionsService {
    private final QuestionsRepository questionsRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.questionsRepository = questionsRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Questions addQuestion(QuestionsDto questionsDto, String user) {
        Questions question = modelMapper.map(questionsDto, Questions.class);
        Category category = categoryRepository.findById(questionsDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        return questionsRepository.save(question);
    }

    @Override
    public Questions updateQuestions(QuestionsDto questionsDto, String user) {
        Questions question = modelMapper.map(questionsDto, Questions.class);
        questionsRepository.findById(questionsDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionsDto.getId()));
        Category category = categoryRepository.findById(questionsDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        return questionsRepository.save(question);
    }

    @Override
    public Set<Questions> findAllQuestions() {
        return new LinkedHashSet<>(questionsRepository.findAll());
    }

    @Override
    public Questions findByQuestionId(Long questionId) {
        return questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
    }

    @Override
    public Set<Questions> findQuestionsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return questionsRepository.findByCategory(category);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        questionsRepository.deleteById(questionId);
    }
}
