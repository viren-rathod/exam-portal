package org.examportal.DTOs.Exam;

import lombok.*;
import org.examportal.Constants.Status;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamDto {
    private Long id;

    private String title;

    private String description;

    private String maxMarks;

    private Timestamp created_at;

    private String created_by;

    private String totalQuestions;

    private Status status;

    private Long examTime;

    private List<Long> categories;
}
