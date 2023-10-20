package org.examportal.DTOs.Exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.examportal.Constants.Status;

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
}
