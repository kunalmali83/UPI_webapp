package com.example.project.security;

import com.example.project.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Spring uses this as username
    @Override
    public String getUsername() {
        return user.getAccountNumber(); // Use accountNumber as username
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Hashed password
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // You can add roles here if needed
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // Custom getter if you need to access the user entity
    public User getUser() {
        return user;
    }
}
