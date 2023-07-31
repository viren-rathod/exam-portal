package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.JWTAuthResponse;
import org.examportal.DTOs.LoginDto;
import org.examportal.DTOs.RegisterDto;
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
@Tag( name = "REST APIs for Authentication" )
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")

public class AuthController {
    private AuthService authService;
    private CityService cityService;
    private StateService stateService;
    private UserService userService;

    public AuthController(AuthService authService,CityService cityService,StateService stateService,UserService userService) {
        this.authService = authService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/states")
    public List<String> getAllStates(){
        List<State> states = stateService.findAllState();
        return states.stream().map(State::getName).toList();
    }

    @GetMapping("/cities/{stateId}")
    public List<String> getCitiesByState(@PathVariable int stateId) {
        List<City> cities = cityService.findCityBYStateId(stateService.findState( stateId));
        return cities.stream().map(City::getName).toList();
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        log.info(user.toString());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
