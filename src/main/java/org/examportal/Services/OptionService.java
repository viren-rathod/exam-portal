package org.examportal.Services;

import org.examportal.DTOs.OptionDto;
import org.examportal.Models.Exam.Options;

import java.util.Set;

public interface OptionService {
    Options create(OptionDto optionDto, String user);

    Options update(OptionDto optionDto, String user);

    Set<Options> findAllByQuestion(Long questionId);

    String saveAll(Set<OptionDto> options, String user);

    void delete(Long optionId);

    String saveAnswer(OptionDto optionDto);
}
