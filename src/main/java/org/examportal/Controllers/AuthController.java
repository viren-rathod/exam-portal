package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Status;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.*;
import org.examportal.Models.City;
import org.examportal.Models.State;
import org.examportal.Services.AuthService;
import org.examportal.Services.CityService;
import org.examportal.Services.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "REST APIs for Authentication")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;
    private final CityService cityService;
    private final StateService stateService;

    public AuthController(AuthService authService, CityService cityService, StateService stateService) {
        this.authService = authService;
        this.stateService = stateService;
        this.cityService = cityService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<BaseResponseDto<JWTAuthResponse>> login(@RequestBody LoginDto loginDto) {
        log.info(String.format("login() - start %s", loginDto));
        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
        Response<JWTAuthResponse> response = new Response<>(jwtAuthResponse);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(UserMessages.LOGGED);
        response.setStatus(Status.INACTIVE);
        response.setToast(true);
        log.info("login() - end ");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<BaseResponseDto<String>> register(@RequestBody RegisterDto registerDto) {
        log.info(String.format("register() - start %s", registerDto));
        String res = authService.register(registerDto);
        Response<String> response = new Response<>(res);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(res);
        response.setStatus(Status.ACTIVE);
        response.setToast(true);
        log.info("register() - end");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/states")
    public ResponseEntity<BaseResponseDto<List<State>>> getAllStates() {
        log.info("getAllStates() - start");
        List<State> states = stateService.findAllState();
        Response<List<State>> response = new Response<>(states, states.size(), states.isEmpty());
        response.setResponseCode(states.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setToast(false);
        if (states.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getAllStates() - %s", states.size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{stateId}")
    public ResponseEntity<BaseResponseDto<List<City>>> getCitiesByState(@PathVariable int stateId) {
        log.info(String.format("getCitiesByState() - start %d", stateId));
        List<City> cities = cityService.findCityBYStateId(stateService.findState(stateId));
        Response<List<City>> response = new Response<>(cities, cities.size(), cities.isEmpty());
        response.setResponseCode(cities.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setToast(false);
        if (cities.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getCitiesByState() - end %s", stateId));
        return ResponseEntity.ok(response);
    }

}
