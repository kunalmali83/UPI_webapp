package com.example.project.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.BankAccount;
import com.example.project.entities.User;
import com.example.project.repository.BalanceRepo;
import com.example.project.repository.UserRepo;

@RestController
@RequestMapping("/api/account")
public class BalanceController {

	@Autowired
	BalanceRepo repo;
	
	
	@GetMapping("/checkBalance")
	
	public ResponseEntity<?> checkBalance(Authentication auth){
		
		String accountNumber=auth.getName();
		Optional <BankAccount>userOpt=repo.findByAccountNumber(accountNumber);
		
		if(userOpt.isPresent()) {
			BankAccount user = userOpt.get();
			BigDecimal balance = BigDecimal.valueOf(user.getBalance().doubleValue());


            // 3. Return the balance
            return ResponseEntity.ok("Your balance is ₹" + balance);
		}
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");

	}
}
