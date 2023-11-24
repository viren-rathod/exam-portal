package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.DTOs.Exam.QuestionsDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Options;
import org.examportal.Models.Exam.Questions;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.examportal.Repositories.Exam.QuestionsRepository;
import org.examportal.Services.Exam.QuestionsService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
        log.info(String.format("addQuestion - start %s", questionsDto));
        Questions question = modelMapper.map(questionsDto, Questions.class);
        Category category = categoryRepository.findById(questionsDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        Questions savedQuestion = questionsRepository.save(question);
        log.info(String.format("addQuestion - end %s", savedQuestion));
        return modelMapper.map(savedQuestion, QuestionsDto.class);
    }

    @Override
    public QuestionsDto updateQuestions(QuestionsDto questionsDto, String user) {
        log.info(String.format("updateQuestions - start %s", questionsDto));
        Questions question = modelMapper.map(questionsDto, Questions.class);
        questionsRepository.findById(questionsDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionsDto.getId()));
        Category category = categoryRepository.findById(questionsDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        Questions savedQuestion = questionsRepository.save(question);
        log.info(String.format("updateQuestions - end %s", question));
        return modelMapper.map(savedQuestion, QuestionsDto.class);
    }

    @Override
    public Set<QuestionsDto> findAllQuestions() {
        log.info("findAllQuestions - start");
        List<Questions> allQuestions = questionsRepository.findAll();
        log.info(String.format("findAllQuestions - end %s", allQuestions));
        return allQuestions.stream().map(question -> modelMapper.map(question, QuestionsDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Page<QuestionsDto> findPaginated(Pageable pageable, String searchData) {
        log.info(String.format("findPaginated - start %s %s", pageable, searchData));
        Page<Questions> page = questionsRepository.findAllWithFilters(searchData, pageable);
        log.info(String.format("findPaginated - end %s", page));
        return page.map(question -> modelMapper.map(question, QuestionsDto.class));
    }

    @Override
    public QuestionsDto findByQuestionId(Long questionId) {
        log.info(String.format("findByQuestionId - start %d", questionId));
        Questions questions = questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        log.info(String.format("findByQuestionId - end %s", questions));
        return modelMapper.map(questions, QuestionsDto.class);
    }

    @Override
    public Set<QuestionsDto> findQuestionsByCategoryId(Long categoryId) {
        log.info(String.format("findQuestionsByCategoryId - start %d", categoryId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Set<Questions> questions = questionsRepository.findByCategory(category);
        log.info(String.format("findQuestionsByCategoryId - end %s", questions));
        return questions.stream().map(question -> modelMapper.map(question, QuestionsDto.class)).collect(Collectors.toSet());
    }

    @Override
    public void deleteQuestion(Long questionId) {
        log.info("deleteQuestion - start");
        questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        questionsRepository.deleteById(questionId);
        log.info("deleteQuestion - end");
    }

    @Override
    public OptionDto findAnswerByQuestionId(Long questionId) {
        log.info(String.format("findAnswerByQuestionId - start %d", questionId));
        Options answer = questionsRepository.findAnswerById(questionId);
        log.info(String.format("findAnswerByQuestionId - end %s", answer));
        return modelMapper.map(answer, OptionDto.class);
    }
}
