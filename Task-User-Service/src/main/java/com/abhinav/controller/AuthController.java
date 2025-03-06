package com.abhinav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.config.JwtProvider;
import com.abhinav.model.User;
import com.abhinav.repository.UserRepository;
import com.abhinav.response.AuthResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        try {
            User isEmailExist = userRepository.findByEmail(user.getEmail());
            if (isEmailExist != null) {
                throw new Exception("Email already exists");
            }
            User createdUser = new User();
            createdUser.setEmail(user.getEmail());
            createdUser.setFullName(user.getFullName());
            createdUser.setRole(user.getRole());
            createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(createdUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                    savedUser.getPassword());
            String token = JwtProvider.generateToken(authentication);
            AuthResponse resp = new AuthResponse(token, "Register Success", true);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse(null, "Error: " + e.getMessage(), false),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody User user) throws Exception {
        try {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(),
                        existingUser.getPassword());
                String token = JwtProvider.generateToken(authentication);
                AuthResponse resp = new AuthResponse(token, "Login Success", true);
                return ResponseEntity.ok(resp);
            } else {
                return new ResponseEntity<>(new AuthResponse(null, "Invalid Email or Password", false),
                        HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse(null, "Error: " + e.getMessage(), false),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
