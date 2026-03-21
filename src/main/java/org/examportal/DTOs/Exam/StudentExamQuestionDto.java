package org.examportal.DTOs.Exam;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentExamQuestionDto {
    private Long examQuestionId;
    private Long questionId;
    private String title;
    private String description;
    private List<OptionDto> options;
    private Long selectedOptionId;
}
