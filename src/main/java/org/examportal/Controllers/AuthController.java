package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.*;
import org.examportal.Models.City;
import org.examportal.Models.State;
import org.examportal.Models.User;
import org.examportal.Services.AuthService;
import org.examportal.Services.CityService;
import org.examportal.Services.StateService;
import org.examportal.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final UserService userService;

    public AuthController(AuthService authService, CityService cityService, StateService stateService, UserService userService) {
        this.authService = authService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<BaseResponseDto<JWTAuthResponse>> login(@RequestBody LoginDto loginDto) {
        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
        Response<JWTAuthResponse> response = new Response<>(jwtAuthResponse);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage("Login Successfully!");
        response.setStatus(true);
        response.setToast(true);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<BaseResponseDto<String>> register(@RequestBody RegisterDto registerDto) {
        String res = authService.register(registerDto);
        Response<String> response = new Response<>(res);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(res);
        response.setStatus(true);
        response.setToast(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/states")
    public ResponseEntity<BaseResponseDto<List<State>>> getAllStates() {
        List<State> states = stateService.findAllState();
        Response<List<State>> response = new Response<>(states);
        response.setResponseCode(states.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setToast(false);
        if (states.isEmpty()) response.setMessage("No Content!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{stateId}")
    public ResponseEntity<BaseResponseDto<List<City>>> getCitiesByState(@PathVariable int stateId) {
        List<City> cities = cityService.findCityBYStateId(stateService.findState(stateId));
        Response<List<City>> response = new Response<>(cities);
        response.setResponseCode(cities.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setToast(false);
        if (cities.isEmpty()) response.setMessage("No Content!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<BaseResponseDto<User>> getCurrentUser(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Response<User> response = new Response<>(user);
        response.setResponseCode(user == null ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setToast(false);
        if (user == null) response.setMessage("No User Found!");
        return ResponseEntity.ok(response);
    }
}
