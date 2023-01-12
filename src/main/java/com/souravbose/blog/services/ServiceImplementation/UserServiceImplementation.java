package com.souravbose.blog.services.ServiceImplementation;

import java.util.List;
import java.util.stream.Collectors;

import com.souravbose.blog.exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.souravbose.blog.entities.User;
import com.souravbose.blog.payloads.UserDto;
import com.souravbose.blog.repositories.UserRepo;
import com.souravbose.blog.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.UserDtoToUser(userDto);
		User createdUser = this.userRepo.save(user);
		return this.UserToUserDto(createdUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userDtoId) {
		User user = this.userRepo.findById(userDtoId).
				orElseThrow(() -> new ResourceNotFoundException("User", "Id", userDtoId + ""));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.UserToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Long userDtoId) {
		User user = this.userRepo.findById(userDtoId).
				orElseThrow(() -> new ResourceNotFoundException("User", "Id", userDtoId + ""));

		return this.UserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.UserToUserDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Long userDtoId) {
		User user = this.userRepo.findById(userDtoId).
				orElseThrow(() -> new ResourceNotFoundException("User", "Id", userDtoId + ""));

		this.userRepo.delete(user);
	}
	
	private User UserDtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		return user;
	}

	private UserDto UserToUserDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);

//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());

		return userDto;
	}

}
