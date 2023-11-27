package org.examportal.DTOs.Exam;

import lombok.*;

import java.sql.Timestamp;

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

    private Timestamp created_at;

    private String created_by;
}
