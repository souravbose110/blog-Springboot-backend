package com.souravbose.blog.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.souravbose.blog.entities.User;
import com.souravbose.blog.exceptions.ResourceNotFoundException;
import com.souravbose.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Loading user by username from databse
		
		User user = this.userRepo.findByEmail(username).
			orElseThrow(()-> new ResourceNotFoundException("User", "email", username));
		
		return user;
	}

}
