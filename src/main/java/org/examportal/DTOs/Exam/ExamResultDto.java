package org.examportal.DTOs.Exam;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamResultDto {
    private Long examId;
    private String examTitle;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer totalMarks;
    private Integer scoredMarks;
}
