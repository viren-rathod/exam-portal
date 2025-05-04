package org.examportal.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.examportal.Models.User;
import org.examportal.Repositories.UserRepository;
import org.examportal.Security.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository; // To fetch the User entity


    // You might want to set the redirect URI dynamically or via configuration
    // Configure your redirect URI for the client-side application
    @Setter
    private String redirectUri = "http://localhost:4200/login-success"; // Example frontend URI

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // The 'authentication' object will contain an OAuth2AuthenticationToken
        // with the principal being the OAuth2User returned by your CustomOAuth2UserService.

        String email = (String) authentication.getPrincipal();

        // Load the User entity from your database based on the email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            // Handle case where user is not found in your DB after successful OAuth2 login
            // This shouldn't happen if your CustomOAuth2UserService is correct,
            // but it's good practice to handle it.
            logger.error("User not found in database after successful OAuth2 authentication for email: " + email);
            getRedirectStrategy().sendRedirect(request, response, "/login?error=oauth2_user_not_found"); // Redirect to an error page
            return;
        }

        // Generate JWT for the authenticated user
        // You might need to adapt your JwtTokenProvider to work with your User entity or UserDetails
        // Assuming your User entity implements UserDetails or you can create a UserDetails from it
//        UserDetails userDetails = (UserDetails) userOptional.get(); // Or convert User to UserDetails
        String token = jwtTokenProvider.generateToken(authentication); // You might need to pass UserDetails instead of Authentication

        // Redirect to your frontend application with the JWT
        // You can pass the token as a query parameter or in a cookie
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // Helper method to clear authentication attributes
//    protected void clearAuthenticationAttributes(HttpServletRequest request) {
//        super.clearAuthenticationAttributes(request);
//    }

    // You might need an authentication failure handler as well

}
