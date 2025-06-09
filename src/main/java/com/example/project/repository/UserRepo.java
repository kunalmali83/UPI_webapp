package com.example.project.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entities.User;

public interface  UserRepo extends JpaRepository<User,Long>{

	Optional<User> findByAccountNumber(String accountNumber);

	

	
}
