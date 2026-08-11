package com.kadu.Dashboard_Backend.controller;

import com.kadu.Dashboard_Backend.model.User;
import com.kadu.Dashboard_Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<User> userOptional =
                userRepository.findByEmail(request.email());

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("E-mail ou senha inválidos");
        }

        User user = userOptional.get();

        boolean passwordCorrect =
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                );

        if (!passwordCorrect) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("E-mail ou senha inválidos");
        }

        return ResponseEntity.ok(user);
    }

    public record LoginRequest(
            String email,
            String password
    ) {}
}