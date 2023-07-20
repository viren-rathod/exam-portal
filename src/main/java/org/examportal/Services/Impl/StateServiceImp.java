package org.examportal.Services.Impl;

import org.examportal.Models.State;
import org.examportal.Repositories.StateRepository;
import org.examportal.Services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImp implements StateService {
    @Autowired
    private StateRepository stateRepository;

    public StateServiceImp(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> findAllState() {
        return stateRepository.findAll();
    }

    public Optional<State> findState(int id) {
        return stateRepository.findById(id);
    }
}
