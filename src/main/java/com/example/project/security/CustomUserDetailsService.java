package com.example.project.security;

import com.example.project.entities.User;
import com.example.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        User user = userRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with account number: " + accountNumber));
        System.out.println("🔐 Loading user for account number: " + accountNumber);

        return new CustomUserDetails(user);
    }
}
