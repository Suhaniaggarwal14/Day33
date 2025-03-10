package com.graymatter.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.graymatter.exception.HandlerExceptions;
import com.graymatter.service.JwtService;
import com.graymatter.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	@Autowired
	JwtService jwtService;
	@Autowired
	MyUserDetailsService userDetailsService;
	@Autowired
	HandlerExceptions handlerExceptions;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authHeader=request.getHeader("Authorization");
		if(authHeader==null|| !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		     return;
		
	}
	
		String token=authHeader.substring(7);
		String email=jwtService.extractUserName(token);
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(email!=null && authentication==null) {
			UserDetails userDetails=userDetailsService.loadUserByUsername(email) ;
			
			if(jwtService.isTokenValid(token,userDetails)) {
				
			UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken
					(userDetails, null,userDetails.getAuthorities());
			authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
			
			
		}
			filterChain.doFilter(request, response);
			


}

}

}
