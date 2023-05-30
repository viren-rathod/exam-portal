package org.examportal.Services.Impl;

import org.examportal.Models.City;
import org.examportal.Models.State;
import org.examportal.Repositories.CityRepository;
import org.examportal.Services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImp implements CityService {
    @Autowired
    private CityRepository cityRepository;

    public CityServiceImp(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAllCity() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> findCityBYStateId(Optional<State> stateId) {
        return cityRepository.findByState(stateId);
    }

//    @Override
//    public List<City> findCity(Optional<State> stateId) {
//        return cityRepository.findByState(stateId);
//    }
}
