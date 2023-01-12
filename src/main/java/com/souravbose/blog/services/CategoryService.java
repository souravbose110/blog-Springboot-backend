package com.souravbose.blog.services;

import java.util.List;

import com.souravbose.blog.payloads.CategoryDto;

public interface CategoryService {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	//delete
	void deleteCategory(Long categoryId);
	
	//get
	CategoryDto getCategory(Long categoryId);
	
	//getall
	List<CategoryDto> getAllCategories();
}
