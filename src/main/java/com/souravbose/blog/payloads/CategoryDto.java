package com.souravbose.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Long categoryId;
	
	@NotEmpty(message = "Title cannot be empty")
	@Size(min = 4, message = "Title should be atleast 4 characters long")
	private String categoryTitle;
	
	@NotEmpty(message = "Description cannot be empty")
	@Size(min = 20, message = "Description must be atleast 20 characters")
	private String categoryDescription;
}
