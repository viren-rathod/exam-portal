package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.DTOs.Exam.QuestionsDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Helper.MapObject;
import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Options;
import org.examportal.Models.Exam.Questions;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.examportal.Repositories.Exam.QuestionsRepository;
import org.examportal.Services.Exam.OptionService;
import org.examportal.Services.Exam.QuestionsService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class QuestionsServiceImpl implements QuestionsService {
    private final QuestionsRepository questionsRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final OptionService optionService;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, OptionService optionService) {
        this.questionsRepository = questionsRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.optionService = optionService;
    }

    @Override
    public Map<String, Object> addQuestion(MapObject<QuestionsDto, List<OptionDto>> requestDto, String user) {
        log.info(String.format("addQuestion - start %s", requestDto));
        QuestionsDto questionsDto = requestDto.getT();
        Questions question = modelMapper.map(questionsDto, Questions.class);
        Map<String, Object> mp = getQuestionInfo(requestDto, user, questionsDto, question);
        log.info(String.format("addQuestion - end %s ", mp));
        return mp;
    }

    private Map<String, Object> getQuestionInfo(MapObject<QuestionsDto, List<OptionDto>> requestDto, String user, QuestionsDto questionsDto, Questions question) {
        Category category = categoryRepository.findById(questionsDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", questionsDto.getCategoryId()));
        question.setCategory(category);
        question.update(user);
        Questions savedQuestion = questionsRepository.save(question);
        List<OptionDto> optionDtoList = requestDto.getK().stream().map(optionDto -> {
            optionDto.setQuestionId(savedQuestion.getId());
            return optionService.create(optionDto, user);
        }).toList();
        Map<String, Object> mp = new HashMap<>();
        mp.put("t", savedQuestion);
        mp.put("k", optionDtoList);
        return mp;
    }

    @Override
    public Map<String, Object> updateQuestions(MapObject<QuestionsDto, List<OptionDto>> requestDto, String user) {
        log.info(String.format("updateQuestions - start %s", requestDto));
        QuestionsDto questionsDto = requestDto.getT();
        Questions question = modelMapper.map(questionsDto, Questions.class);
        questionsRepository.findById(questionsDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionsDto.getId()));
        Map<String, Object> mp = getQuestionInfo(requestDto, user, questionsDto, question);
        log.info(String.format("updateQuestions - end %s", mp));
        return mp;
    }

    @Override
    public Set<Map<String, Object>> findAllQuestions() {
        log.info("findAllQuestions - start");
        List<Questions> allQuestions = questionsRepository.findAll();
        log.info(String.format("findAllQuestions - end %s", allQuestions));
        return getQuestionsMap(allQuestions.stream());
    }

    @Override
    public Page<Map<String, Object>> findPaginated(Pageable pageable, String searchData) {
        log.info(String.format("findPaginated - start %s %s", pageable, searchData));
        Page<Questions> page = questionsRepository.findAllWithFilters(searchData, pageable);
        log.info(String.format("findPaginated - end %s", page));
        return page.map(this::getQuestionObject);
    }

    @Override
    public Map<String, Object> findByQuestionId(Long questionId) {
        log.info(String.format("findByQuestionId - start %d", questionId));
        Questions questions = questionsRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        log.info(String.format("findByQuestionId - end %s", questions));
        QuestionsDto questionsDto = modelMapper.map(questions, QuestionsDto.class);
        Set<OptionDto> allOptions = optionService.findAllByQuestion(questions.getId());
        Map<String, Object> mp = new HashMap<>();
        mp.put("t", questionsDto);
        mp.put("k", allOptions);
        return mp;
    }

    @Override
    public Set<Map<String, Object>> findQuestionsByCategoryId(Long categoryId) {
        log.info(String.format("findQuestionsByCategoryId - start %d", categoryId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Set<Questions> questions = questionsRepository.findByCategory(category);
        log.info(String.format("findQuestionsByCategoryId - end %s", questions));
        return getQuestionsMap(questions.stream());
    }

    @Override
    public void deleteQuestion(Long questionId) {
        log.info("deleteQuestion - start");
        questionsRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
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

    private Set<Map<String, Object>> getQuestionsMap(Stream<Questions> stream) {
        return stream.map(this::getQuestionObject).collect(Collectors.toSet());
    }

    public Map<String, Object> getQuestionObject(Questions question) {
        QuestionsDto questionsDto = modelMapper.map(question, QuestionsDto.class);
        Set<OptionDto> allOptions = optionService.findAllByQuestion(question.getId());
        Map<String, Object> mp = new HashMap<>();
        mp.put("t", questionsDto);
        mp.put("k", allOptions);
        return mp;
    }
}