package org.examportal.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "college_master")
public class College extends BaseEntity {
    private String name;

    @OneToOne(mappedBy = "college", fetch = FetchType.LAZY)
    private Candidate candidate;
}
