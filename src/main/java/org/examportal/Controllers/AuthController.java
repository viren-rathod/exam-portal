package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.*;
import org.examportal.Models.City;
import org.examportal.Models.Role;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/getCurrentUser")
    public ResponseEntity<BaseResponseDto<Map<String, Object>>> getCurrentUser(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Response<Map<String, Object>> response = new Response<>();
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            response.setData(map);
            response.setMessage("User found!");
            response.setResponseCode(HttpStatus.OK.value());
            response.getData().put("id", user.getId());
//            response.getData().put("created_at", user.getCreated_at());
//            response.getData().put("created_by", user.getCreated_by());
            response.getData().put("username", user.getUsername());
            response.getData().put("email", user.getEmail());
            response.getData().put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        } else {
            response.setResponseCode(HttpStatus.NO_CONTENT.value());
            response.setMessage("No User found!");
        }
        return ResponseEntity.ok(response);
    }
}
