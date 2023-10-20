package org.examportal.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.examportal.Models.User;
import org.examportal.Repositories.UserRepository;
import org.examportal.Services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existByUsername(String username) {
        log.info(String.format("existByUsername - start %s", username));
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        log.info(String.format("findByEmail - start %s", email));
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not Found!"));
    }
}
