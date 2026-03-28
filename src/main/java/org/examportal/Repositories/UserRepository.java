package org.examportal.Repositories;

import org.examportal.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // Fetch user with roles eagerly
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :val OR u.email = :val")
    Optional<User> findByUsernameOrEmailWithRoles(@Param("val") String usernameOrEmail);
}
