package org.examportal.Models.Exam;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.examportal.Models.BaseEntity;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String title;

    private String description;
}
