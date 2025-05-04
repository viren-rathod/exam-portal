package org.examportal.Config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Helper.OAuth2AuthenticationFailureHandler;
import org.examportal.Helper.OAuth2AuthenticationSuccessHandler;
import org.examportal.Repositories.UserRepository;
import org.examportal.Security.CustomOAuth2UserService;
import org.examportal.Security.JwtAuthenticationEntryPoint;
import org.examportal.Security.JwtAuthenticationFilter;
import org.examportal.Security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableMethodSecurity()
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAuthenticationFilter authenticationFilter;

    private final CustomOAuth2UserService customOAuth2UserService; // You will create this

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter,
                          CustomOAuth2UserService customOAuth2UserService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)//  .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers("/api/auth/getCurrentUser").authenticated()
                        .requestMatchers("/oauth2/**").permitAll() // Allow access to OAuth2 endpoints
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth2->oauth2.userInfoEndpoint(userInfo->userInfo.userService(customOAuth2UserService)
                )
                                .successHandler(oauth2AuthenticationSuccessHandler()) // You will create this
                                .failureHandler(oauth2AuthenticationFailureHandler()) // You will create this
                         );

        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // Define beans for success and failure handlers
    @Bean
    public OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        return new OAuth2AuthenticationSuccessHandler(jwtTokenProvider,userRepository); // Implement this class
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(); // Implement this class
    }
}