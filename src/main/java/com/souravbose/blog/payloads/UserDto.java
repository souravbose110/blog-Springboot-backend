package com.souravbose.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private long id;
	
	@NotEmpty
	@Size(min = 3, message = "Username must be minimum of 3 characters!!")
	private String name;
	
	@Email(message = "Email address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 8, max = 16, message = "Password must be between 8 to 16 characters")
	private String password;
	
	@NotEmpty
	private String about;	
}
