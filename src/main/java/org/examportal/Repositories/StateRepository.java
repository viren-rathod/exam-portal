package org.examportal.Repositories;

import org.examportal.Models.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {
    List<State> findByName(String name);
}
