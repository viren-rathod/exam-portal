package org.examportal.Config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Security.JwtAuthenticationEntryPoint;
import org.examportal.Security.JwtAuthenticationFilter;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
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
    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("133559305202-6f28lun6n9gk2o9ne39its99a2d4b6g1.apps.googleusercontent.com")
                .clientSecret("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")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email", "address", "phone")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)  //  .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(authz -> {
                     authz
                            // .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                             .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                             .requestMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
                             .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                             .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                             .requestMatchers("/swagger-ui/**").permitAll()
                             .requestMatchers("/v3/api-docs/**").permitAll()
                             .requestMatchers("/api/google").authenticated()
                             .anyRequest().authenticated();
                    }
                        )
                // .oauth2Login(Customizer.withDefaults())
                .oauth2Login(oauth2Client -> {
                });
//                .exceptionHandling( exception -> exception
//                        .authenticationEntryPoint(authenticationEntryPoint)
//                );
//                .sessionManagement( session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );

        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}

//@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//
//                .authorizeHttpRequests(a -> a
//                        .requestMatchers("/auth/**").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/formlogin").permitAll()
//                )
//                .oauth2Login(oauth2Client -> {
////                    oauth2Client.clientRegistrationRepository(clientRegistrationRepository());
//                });
//        return http.build();
//    }