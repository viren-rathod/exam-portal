package org.examportal.Models.Exam;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.examportal.Constants.Status;
import org.examportal.Models.BaseEntity;
import org.examportal.Models.Candidate;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam")
@ToString
public class Exam extends BaseEntity {
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    private Long examTime;

    private String maxMarks;

    private String totalQuestions;

    private String examCode;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "exam_category",
            joinColumns = @JoinColumn(name = "exam_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
    private List<Candidate> candidate;
}

