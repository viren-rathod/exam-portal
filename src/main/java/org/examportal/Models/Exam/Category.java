package org.examportal.Models.Exam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.examportal.Models.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
@ToString
public class Category extends BaseEntity {
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String title;

    private String description;
}
