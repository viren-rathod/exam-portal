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
        log.info(String.format("create - start %s", optionDto));
        Options option = modelMapper.map(optionDto, Options.class);
        Questions question = questionsRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", optionDto.getQuestionId()));
        option.setQuestion(question);
        option.update(user);
        Options savedOption = optionRepository.save(option);
        log.info(String.format("create - end  %s", option));
        return modelMapper.map(savedOption, OptionDto.class);
    }

    @Override
    public OptionDto update(OptionDto optionDto, String user) {
        log.info(String.format("update - start %s", optionDto));
        optionRepository.findById(optionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", optionDto.getId()));
        Options option = modelMapper.map(optionDto, Options.class);
        Questions question = questionsRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", optionDto.getQuestionId()));
        option.setQuestion(question);
        option.update(user);
        Options savedOption = optionRepository.save(option);
        log.info(String.format("update - end %s", option));
        return modelMapper.map(savedOption, OptionDto.class);
    }

    @Override
    public Set<OptionDto> findAllByQuestion(Long questionId) {
        log.info(String.format("findAllByQuestion - start %d", questionId));
        Questions question = questionsRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        Set<Options> options = optionRepository.findByQuestion(question);
        log.info(String.format("findAllByQuestion - %s", options));
        return options.stream().map(q -> modelMapper.map(q, OptionDto.class)).collect(Collectors.toSet());
    }

    @Override
    public String saveAll(Set<OptionDto> options, String user) {
        log.info(String.format("saveAll - start %s", options));
        Set<OptionDto> optionsSet = options.stream().map(option -> create(option, user)).collect(Collectors.toSet());
        log.info(String.format("saveAll - end %s", optionsSet));
        return OptionMessages.OPTION_ADDED;
    }

    @Override
    public void delete(Long optionId) {
        log.info(String.format("delete - start %d", optionId));
        optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", optionId));
        optionRepository.deleteById(optionId);
        log.info("delete - end");
    }

    @Override
    public String saveAnswer(OptionDto optionDto) {
        log.info(String.format("saveAnswer - start %s", optionDto));
        optionRepository.findById(optionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", optionDto.getId()));
        Questions question = questionsRepository.findById(optionDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", optionDto.getQuestionId()));
        Options option = modelMapper.map(optionDto, Options.class);
        question.setAnswer(option);
        questionsRepository.save(question);
        log.info(String.format("saveAnswer - end %s", question));
        return OptionMessages.ANSWER_SET;
    }
}
