package org.examportal.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "state_master")
public class State extends BaseEntity {
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "state", fetch = FetchType.EAGER)
    private List<City> cities = new ArrayList<>();
}
