package org.examportal.Models.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.examportal.Models.BaseEntity;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "questions")
public class Questions extends BaseEntity {
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Set<Options> options;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "question_answer",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Options answer;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "question_category",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Category category;
}
