package org.examportal.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "state_master")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stateId;
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "state", fetch = FetchType.EAGER)
    private List<City> cities = new ArrayList<>();
}
