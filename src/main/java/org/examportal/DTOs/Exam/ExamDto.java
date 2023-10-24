package org.examportal.DTOs.Exam;

import lombok.*;
import org.examportal.Constants.Status;

import java.util.Set;

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

    private String examCode;

    private Status status;

    private Long examTime;

    private Set<Long> categoryList;
}
