package org.examportal.Services;

import org.examportal.Models.City;
import org.examportal.Models.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CityService {
    public List<City> findAllCity();

    public List<City> findCityBYStateId(Optional<State> stateId);
}
