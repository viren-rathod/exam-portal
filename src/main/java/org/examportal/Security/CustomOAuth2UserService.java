package org.examportal.Security;

import org.examportal.Constants.UserRole;
import org.examportal.Models.Role;
import org.examportal.Models.User;
import org.examportal.Repositories.RoleRepository;
import org.examportal.Repositories.UserRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // You'll need a way to extract user information from the OAuth2User
        // The structure of oAuth2User.getAttributes() depends on the provider (Google in this case)
        // You might want to create an OAuth2UserInfo abstraction for different providers

        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        String provider = userRequest.getClientRegistration().getRegistrationId(); // e.g., "google"
        String providerId = oAuth2User.getName(); // The subject from Google

        if (email == null || email.trim().isEmpty()) {
            throw new OAuth2AuthenticationException("Email not received from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        // Optional: Check if the existing user is already linked to this provider
        // If not, you might want to link the account or handle the case where
        // a user tries to log in with Google but already has a password account
        // without a Google link.
        // For simplicity here, we assume if the email exists, it's the same user.
        // Register new user
        user = userOptional.orElseGet(() -> registerNewUser(userRequest, oAuth2User));

        // You might want to wrap the OAuth2User in your own UserDetails implementation
        // or modify your existing UserDetails to include OAuth2 information.
        // For simplicity, let's return the original OAuth2User for now, but you
        // will likely need a more sophisticated approach to integrate with your JWT generation.

        return oAuth2User;
    }

    private User registerNewUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        User user = new User();
        user.setEmail((String) oAuth2User.getAttributes().get("email"));
        user.setUsername((String) oAuth2User.getAttributes().get("name"));
        // You might need to set a default role here
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(UserRole.valueOf("USER")).ifPresent(roles::add); // Assuming a default "ROLE_USER"
        user.setRoles(roles);
        // You might want to set a flag indicating it's an OAuth2 user
//        user.setProvider(userRequest.getClientRegistration().getRegistrationId()); // e.g., "google"
//        user.setProviderId(oAuth2User.getName()); // The subject from Google

        return userRepository.save(user);
    }


}
