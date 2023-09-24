package org.examportal.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsDto {
    private Long id;

    private String title;

    private String description;

    private Long categoryId;
}
