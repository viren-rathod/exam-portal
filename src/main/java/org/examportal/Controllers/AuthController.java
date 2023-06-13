package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.JWTAuthResponse;
import org.examportal.DTOs.LoginDto;
import org.examportal.DTOs.RegisterDto;
import org.examportal.Exceptions.ExamAPIException;
import org.examportal.Models.City;
import org.examportal.Models.State;
import org.examportal.Services.AuthService;
import org.examportal.Services.CityService;
import org.examportal.Services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Tag( name = "REST APIs for Authentication" )
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    private CityService cityService;
    private StateService stateService;

    public AuthController(AuthService authService,CityService cityService,StateService stateService) {
        this.authService = authService;
        this.stateService = stateService;
        this.cityService = cityService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @GetMapping("/google")
    public ResponseEntity<String> currentUser (OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        log.info(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString());
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
         String token = null;
        if(Boolean.TRUE.equals(authService.existByUsername(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString()))){
            LoginDto loginDto = new LoginDto(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString(),"$Viren@_bv67989!--*");
            token = authService.login(loginDto);
            jwtAuthResponse.setAccessToken(token);
        }
        else {
            RegisterDto registerDto = new RegisterDto(oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name").toString(),oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString(),oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString(),"$Viren@_bv67989!--*");
            token = authService.register(registerDto);
        }
        return ResponseEntity.ok(token);
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
}
