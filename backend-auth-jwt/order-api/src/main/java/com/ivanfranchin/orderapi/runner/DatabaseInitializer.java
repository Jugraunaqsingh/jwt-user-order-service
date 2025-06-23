package com.ivanfranchin.orderapi.runner;

import com.ivanfranchin.orderapi.user.User;
import com.ivanfranchin.orderapi.security.SecurityConfig;
import com.ivanfranchin.orderapi.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Always ensure admin user exists
        String adminUsername = "adminraunaq";
        if (!userService.hasUserWithUsername(adminUsername)) {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setName("Admin");
            adminUser.setEmail("admin@mycompany.com");
            adminUser.setRole(SecurityConfig.ADMIN);

            userService.saveUser(adminUser);
            log.info("Default admin user created.");
        } else {
            log.info("Admin user already exists.");
        }
    }
}
