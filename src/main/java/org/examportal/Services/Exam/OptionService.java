package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.OptionDto;

import java.util.Set;

public interface OptionService {
    OptionDto create(OptionDto optionDto, String user);

    OptionDto update(OptionDto optionDto, String user);

    Set<OptionDto> findAllByQuestion(Long questionId);

    String saveAll(Set<OptionDto> options, String user);

    void delete(Long optionId);

    String saveAnswer(OptionDto optionDto, String user);
}
