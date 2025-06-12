package com.example.project.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.BankAccountReq;
import com.example.project.dto.UserLoginRequest;
import com.example.project.dto.UserResponse;
import com.example.project.dto.UserSignUpReq;
import com.example.project.entities.BankAccount;
import com.example.project.entities.User;
import com.example.project.repository.BalanceRepo;
import com.example.project.repository.UserRepo;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BalanceRepo balanceRepo;
	
	public UserController() {
        System.out.println("✅ UserController loaded");
    }
	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody UserSignUpReq request) {
	    if (userRepo.findByAccountNumber(request.getAccountNumber()).isPresent()) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists.");
	    }

	    User newUser = new User();
	    newUser.setName(request.getName());
	    newUser.setEmail(request.getEmail());
	    newUser.setPassword(passwordEncoder.encode(request.getPassword()));
	    newUser.setAddress(request.getAddress());
	    newUser.setMobileNumber(request.getMobileNumber());
	    newUser.setAccountNumber(request.getAccountNumber());

	    List<BankAccount> savedAccounts = new ArrayList<>();
	    for (BankAccountReq accReq : request.getAccounts()) {
	        BankAccount account = new BankAccount();
	        account.setAccountNumber(accReq.getAccountNumber());
	        account.setAccountHolder(accReq.getAccountHolder());
	        account.setBalance(BigDecimal.ZERO);
	        account.setUser(newUser); // VERY IMPORTANT
	        account.setBankName(accReq.getBankName());

	        // Generate unique UPI ID
	        String baseUpiId = accReq.getAccountHolder().toLowerCase().replaceAll("\\s+", "") + "@" + accReq.getBankName().toLowerCase();
	        String uniqueUpiId = baseUpiId;
	        int counter = 1;
	        while (balanceRepo.existsByUpiId(uniqueUpiId)) {
	            uniqueUpiId = baseUpiId + counter;
	            counter++;
	        }
	        account.setUpiId(uniqueUpiId);

	        savedAccounts.add(account);
	    }

	    newUser.setAccounts(savedAccounts);
	    userRepo.save(newUser); // Save user and cascade to accounts

	    return ResponseEntity.ok("User registered with multiple bank accounts successfully.");
	}


	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest,
	                               HttpServletRequest request) {
	    try {
	        // Step 1: Authenticate using Spring Security
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getAccountNumber(),  // Username = accountNumber
	                loginRequest.getPassword()        // Raw password
	            )
	        );

	        // Step 2: Store auth in security context
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	  

	        // Optional: Fetch user details to return a response
	        Optional<User> userOpt = userRepo.findByAccountNumber(loginRequest.getAccountNumber());
	        if (userOpt.isPresent()) {
	            User user = userOpt.get();
	            UserResponse response = new UserResponse(user.getName(), user.getEmail());
	            return ResponseEntity.ok(response);
	        }

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User found but something went wrong");

	    } catch (AuthenticationException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	}



	
}
