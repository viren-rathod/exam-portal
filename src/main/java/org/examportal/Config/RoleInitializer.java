package org.examportal.Config;

import org.examportal.Constants.UserRole;
import org.examportal.Models.Role;
import org.examportal.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // Check if ADMIN role exists, if not create it
        if (roleRepository.findByName(UserRole.ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(UserRole.ADMIN);
            adminRole.setCreated_by("admin");
            adminRole.setCreated_at(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(adminRole);
            log.info("'ADMIN' role created.");
        }

        // Check if USER role exists, if not create it
        if (roleRepository.findByName(UserRole.USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(UserRole.USER);
            userRole.setCreated_by("admin");
            userRole.setCreated_at(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(userRole);
            log.info("'USER' role created.");
        }
    }
}
