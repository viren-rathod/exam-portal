package org.examportal.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "college_master")
public class College extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private Set<Candidate> candidates = new LinkedHashSet<>();
}
