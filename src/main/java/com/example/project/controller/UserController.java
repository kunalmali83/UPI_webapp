package com.example.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.UserLoginRequest;
import com.example.project.dto.UserResponse;
import com.example.project.dto.UserSignUpReq;
import com.example.project.entities.User;
import com.example.project.repository.UserRepo;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserController() {
        System.out.println("✅ UserController loaded");
    }
	@PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserSignUpReq request) {
        if (userRepo.findByAccountNumber(request.getAccountNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("UPI ID already exists.");
        }
        
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setUpiId(request.getUpiId());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setAddress(request.getAddress());
        newUser.setMobileNumber(request.getMobileNumber());
        newUser.setAccountNumber(request.getAccountNumber());

        userRepo.save(newUser);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")

    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        Optional<User> userOpt = userRepo.findByAccountNumber(loginRequest.getAccountNumber());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // ✅ Proper password comparison (raw vs. encrypted)
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                UserResponse response = new UserResponse(user.getName(), user.getEmail(), user.getUpiId());
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid UPI ID or password.");
    }
	
}
