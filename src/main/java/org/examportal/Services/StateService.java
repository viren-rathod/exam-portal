package org.examportal.Services;

import org.examportal.Models.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StateService {
    public List<State> findAllState();
    public Optional<State> findState(int id);
}
