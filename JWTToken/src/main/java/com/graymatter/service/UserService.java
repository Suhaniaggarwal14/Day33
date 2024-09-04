package com.graymatter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graymatter.dto.RegUserDto;
import com.graymatter.entities.User;
import com.graymatter.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository repository;
	
	public List<User> getAllUsers(){
		return repository.findAll();
		
		
		
	}
	

}
