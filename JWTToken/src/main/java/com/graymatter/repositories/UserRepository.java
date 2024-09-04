package com.graymatter.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graymatter.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	

	Optional<User> findByEmail(String username); 

}
