package com.kadu.Dashboard_Backend.controller;

import com.kadu.Dashboard_Backend.model.User;
import com.kadu.Dashboard_Backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        String passwordEncrypted =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordEncrypted);

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }
}