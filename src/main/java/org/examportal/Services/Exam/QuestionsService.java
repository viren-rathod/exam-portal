package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.DTOs.Exam.QuestionsDto;
import org.examportal.Helper.MapObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuestionsService {
    Map<String, Object> addQuestion(MapObject<QuestionsDto, List<OptionDto>> requestDto, String user);

    Map<String, Object> updateQuestions(MapObject<QuestionsDto, List<OptionDto>> requestDto, String user);

    Set<Map<String, Object>> findAllQuestions();

    Page<Map<String, Object>> findPaginated(Pageable pageable, String searchData);

    Map<String, Object> findByQuestionId(Long questionId);

    Set<Map<String, Object>> findQuestionsByCategoryId(Long categoryId);

    void deleteQuestion(Long questionId);

    OptionDto findAnswerByQuestionId(Long questionId);
}
