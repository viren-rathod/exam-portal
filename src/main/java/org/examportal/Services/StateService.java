package org.examportal.Services;

import org.examportal.Models.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StateService {
    List<State> findAllState();

    Optional<State> findState(int id);
}
