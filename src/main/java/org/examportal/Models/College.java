package org.examportal.Models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "college_master")
@ToString
public class College extends BaseEntity {
    private String name;

    @OneToOne(mappedBy = "college", fetch = FetchType.LAZY)
    private Candidate candidate;
}
