package com.ivanfranchin.orderapi.runner;

import com.ivanfranchin.orderapi.user.User;
import com.ivanfranchin.orderapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        createUserIfNotExists("admin_temp123", "testpass123", "Admin Tester", "admin_temp123@mycompany.com", "ADMIN");
    }

    private void createUserIfNotExists(String username, String rawPassword, String name, String email, String role) {
        if (!userService.hasUserWithUsername(username)) {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            User user = new User(username, encodedPassword, name, email, role);
            userService.saveUser(user);
            System.out.printf("✅ Created default user: %s (%s)%n", username, role);
            System.out.printf("🔐 Encoded password hash: %s%n", encodedPassword);
        } else {
            System.out.printf("ℹ️ User %s already exists. Skipping.%n", username);
        }
    }
}
