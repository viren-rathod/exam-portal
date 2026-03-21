package org.examportal.DTOs.Exam;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubmitAnswerDto {
    private Long examQuestionId;
    private Long selectedOptionId;
}
