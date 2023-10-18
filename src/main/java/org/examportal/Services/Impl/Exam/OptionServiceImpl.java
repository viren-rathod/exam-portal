package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.OptionMessages;
import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Options;
import org.examportal.Models.Exam.Questions;
import org.examportal.Repositories.Exam.OptionRepository;
import org.examportal.Repositories.Exam.QuestionsRepository;
import org.examportal.Services.Exam.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;
    private final QuestionsRepository questionsRepository;
    private final ModelMapper modelMapper;

    public OptionServiceImpl(OptionRepository optionRepository, QuestionsRepository questionsRepository, ModelMapper modelMapper) {
        this.optionRepository = optionRepository;
        this.questionsRepository = questionsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OptionDto create(OptionDto optionDto, String user) {
        Options option = modelMapper.map(optionDto, Options.class);
        Questions question = questionsRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", optionDto.getQuestionId()));
        option.setQuestion(question);
        option.update(user);
        Options savedOption = optionRepository.save(option);
        return modelMapper.map(savedOption, OptionDto.class);
    }

    @Override
    public OptionDto update(OptionDto optionDto, String user) {
        optionRepository.findById(optionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", optionDto.getId()));
        Options option = modelMapper.map(optionDto, Options.class);
        Questions question = questionsRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", optionDto.getQuestionId()));
        option.setQuestion(question);
        option.update(user);
        Options savedOption = optionRepository.save(option);
        return modelMapper.map(savedOption, OptionDto.class);
    }

    @Override
    public Set<OptionDto> findAllByQuestion(Long questionId) {
        Questions question = questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        Set<Options> options = optionRepository.findByQuestion(question);
        return options.stream().map(q -> modelMapper.map(q, OptionDto.class)).collect(Collectors.toSet());
    }

    @Override
    public String saveAll(Set<OptionDto> options, String user) {
        Set<OptionDto> optionsSet = options.stream().map(option -> create(option, user)).collect(Collectors.toSet());
        return OptionMessages.OPTION_ADDED;
    }

    @Override
    public void delete(Long optionId) {
        optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", optionId));
        optionRepository.deleteById(optionId);
    }

    @Override
    public String saveAnswer(OptionDto optionDto) {
        optionRepository.findById(optionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", optionDto.getId()));
        Questions question = questionsRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", optionDto.getQuestionId()));
        Options option = modelMapper.map(optionDto, Options.class);
        question.setAnswer(option);
        questionsRepository.save(question);
        return OptionMessages.ANSWER_SET;
    }
}
