package com.graymatter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graymatter.config.UserPrincipal;
import com.graymatter.dto.LoginResponse;
import com.graymatter.dto.LoginUserDto;
import com.graymatter.dto.RegUserDto;
import com.graymatter.entities.User;
import com.graymatter.service.AuthenticationService;
import com.graymatter.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
    JwtService jwtService;
	@Autowired
	AuthenticationService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody RegUserDto regUserDto){
		User regUser=authService.signup(regUserDto);
		return ResponseEntity.ok(regUser);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto){
		User authUser=authService.login(loginUserDto);
		String token=jwtService.generateToken(new UserPrincipal(authUser));
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.setToken(token);
		loginResponse.setExpirationTime(jwtService.expirationTime());
		return ResponseEntity.ok(loginResponse);
		
	}
	

}
