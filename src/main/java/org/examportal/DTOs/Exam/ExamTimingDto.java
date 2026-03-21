package org.examportal.DTOs.Exam;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamTimingDto {
    private LocalDateTime examEndTime;
    private LocalDateTime serverTime;
    private String examTitle;
    private Long examTimeMinutes;
}
