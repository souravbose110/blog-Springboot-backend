package com.souravbose.blog.controllers;

import com.souravbose.blog.payloads.ApiResponse;
import com.souravbose.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.souravbose.blog.payloads.UserDto;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = this.userService.createUser(userDto);
		return ResponseEntity.ok(createdUser);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
		return ResponseEntity.ok(new ApiResponse("User Deleted Successfully", true));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
		UserDto userDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> userDtos = this.userService.getAllUsers();
		return ResponseEntity.ok(userDtos);
	}
}
