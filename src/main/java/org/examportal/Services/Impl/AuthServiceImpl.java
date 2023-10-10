package org.examportal.Services.Impl;

import org.examportal.DTOs.JWTAuthResponse;
import org.examportal.DTOs.LoginDto;
import org.examportal.DTOs.RegisterDto;
import org.examportal.Exceptions.ExamAPIException;
import org.examportal.Models.Role;
import org.examportal.Models.User;
import org.examportal.Repositories.RoleRepository;
import org.examportal.Repositories.UserRepository;
import org.examportal.Security.JwtTokenProvider;
import org.examportal.Services.AuthService;
import org.examportal.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (Boolean.TRUE.equals(userService.existByUsername(registerDto.getUsername()))) {
            throw new ExamAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(registerDto.getEmail()))) {
            throw new ExamAPIException(HttpStatus.BAD_REQUEST, "E - mail already exists!");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.update(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();
        Optional<Role> opt = roleRepository.findByName("USER");
        Role userRole = new Role();
        if (opt.isPresent()) userRole = opt.get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "Registered successfully!";
    }

}
