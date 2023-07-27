package org.examportal.Services;

import org.examportal.Models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean existByUsername(String username);

    User findByEmail(String email);
}
