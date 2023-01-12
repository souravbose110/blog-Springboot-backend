package com.souravbose.blog.services;

import java.util.List;

import com.souravbose.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto user, Long userDtoId);
	
	UserDto getUserById(Long userDtoId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long userDtoId);
}
