package org.examportal.Services;

import org.examportal.DTOs.JWTAuthResponse;
import org.examportal.DTOs.LoginDto;
import org.examportal.DTOs.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
