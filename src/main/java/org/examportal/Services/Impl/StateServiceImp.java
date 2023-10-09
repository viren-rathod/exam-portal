package org.examportal.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.CityStateDto;
import org.examportal.Models.City;
import org.examportal.Models.State;
import org.examportal.Repositories.CityRepository;
import org.examportal.Repositories.StateRepository;
import org.examportal.Services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StateServiceImp implements StateService {
    @Autowired
    private StateRepository stateRepository;
    private CityRepository cityRepository;

    public StateServiceImp(StateRepository stateRepository, CityRepository cityRepository) {
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<State> findAllState() {
        return stateRepository.findAll();
    }

    public Optional<State> findState(int id) {
        return stateRepository.findById(id);
    }

    @Override
    public String AddCityStateData(CityStateDto cityStateDto, String user) {
        log.info(String.format("CityStateDto() ==>  %s", cityStateDto.getState().getName()));
        List<State> states = stateRepository.findByName(cityStateDto.getState().getName());
        if (!states.isEmpty()) return "State Already exist!";
        State state = new State();
        state.setName(cityStateDto.getState().getName());
        state.update(user);
        log.info(String.format("State() ==> %s", state));
        stateRepository.save(state);

        for (String cityName : cityStateDto.getCities()) {
            List<City> cities = cityRepository.findByName(cityName);
            if (!cities.isEmpty()) continue;
            City city = new City();
            city.setName(cityName);
            city.setState(state);
            city.update(user);
            log.info(String.format("City() ==> %s", city));
            cityRepository.save(city);
        }
        return "Added!";
    }

    @Override
    public String AddAllCityStateData(List<CityStateDto> cityStateDtos, String user) {
        for (CityStateDto cityStateDto : cityStateDtos) {
            AddCityStateData(cityStateDto, user);
        }
        return "Added all Data";
    }
}
