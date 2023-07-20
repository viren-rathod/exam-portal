package org.examportal.Repositories;

import org.examportal.Models.City;
import org.examportal.Models.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByState(Optional<State> stateId);
//    List<City> findByState(State stateId);
}
