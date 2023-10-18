package org.examportal.DTOs.Exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
    private Long id;

    private String title;

    private String description;

    private String maxMarks;

    private String totalQuestions;

    private Boolean isActive;

    private Long examTime;
}
