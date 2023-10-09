package org.examportal.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city_master")
@ToString
public class City extends BaseEntity {
    private String name;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "state_id")
    private State state;
}