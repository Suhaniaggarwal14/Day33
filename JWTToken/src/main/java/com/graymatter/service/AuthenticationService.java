package com.graymatter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graymatter.dto.LoginUserDto;
import com.graymatter.dto.RegUserDto;
import com.graymatter.entities.User;
import com.graymatter.repositories.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	UserRepository repository;
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	PasswordEncoder passwordEncoder;

	public User signup(RegUserDto regUserDto) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUsername(regUserDto.getUsername());
		user.setEmail(regUserDto.getEmail());
		user.setPassword(passwordEncoder.encode(regUserDto.getPassword()));
		return repository.save(user);
	}

	public User login(LoginUserDto loginUserDto) {
		// TODO Auto-generated method stub
		authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
		return repository.findByEmail(loginUserDto.getEmail()).get();
	}
	
	

}
