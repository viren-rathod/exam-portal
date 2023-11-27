package org.examportal.DTOs.Exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {
    private Long id;

    private String title;

    private Long questionId;

    private Timestamp created_at;

    private String created_by;
}
