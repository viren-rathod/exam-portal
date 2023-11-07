package org.examportal.DTOs.Exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.examportal.Constants.Status;

import java.util.List;

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

    private Status status;

    private Long examTime;

    private List<Long> categories;
}
