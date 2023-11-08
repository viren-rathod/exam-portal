package org.examportal.DTOs.Exam;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionsDto {
    private Long id;

    private String title;

    private String description;

    private Long categoryId;
}
