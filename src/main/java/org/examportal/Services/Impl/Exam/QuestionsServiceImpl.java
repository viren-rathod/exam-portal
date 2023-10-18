package org.examportal.Services.Impl.Exam;

import org.examportal.DTOs.Exam.QuestionsDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Questions;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.examportal.Repositories.Exam.QuestionsRepository;
import org.examportal.Services.Exam.QuestionsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public QuestionsDto addQuestion(QuestionsDto questionsDto, String user) {
        Questions question = modelMapper.map(questionsDto, Questions.class);
        Category category = categoryRepository.findById(questionsDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        Questions savedQuestion = questionsRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionsDto.class);
    }

    @Override
    public QuestionsDto updateQuestions(QuestionsDto questionsDto, String user) {
        Questions question = modelMapper.map(questionsDto, Questions.class);
        questionsRepository.findById(questionsDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionsDto.getId()));
        Category category = categoryRepository.findById(questionsDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        Questions savedQuestion = questionsRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionsDto.class);
    }

    @Override
    public Set<QuestionsDto> findAllQuestions() {
        List<Questions> allQuestions = questionsRepository.findAll();
        return allQuestions.stream().map(question -> modelMapper.map(question, QuestionsDto.class)).collect(Collectors.toSet());
    }

    @Override
    public QuestionsDto findByQuestionId(Long questionId) {
        Questions questions = questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        return modelMapper.map(questions, QuestionsDto.class);
    }

    @Override
    public Set<QuestionsDto> findQuestionsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Set<Questions> questions = questionsRepository.findByCategory(category);
        return questions.stream().map(question -> modelMapper.map(question, QuestionsDto.class)).collect(Collectors.toSet());
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        questionsRepository.deleteById(questionId);
    }
}
