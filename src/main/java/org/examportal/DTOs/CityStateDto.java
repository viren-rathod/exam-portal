package org.examportal.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.examportal.Models.State;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityStateDto {
    private Long id;
    private List<String> cities;
    private State state;
}