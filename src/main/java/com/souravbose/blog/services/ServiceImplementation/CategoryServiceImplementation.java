package com.souravbose.blog.services.ServiceImplementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souravbose.blog.entities.Category;
import com.souravbose.blog.exceptions.ResourceNotFoundException;
import com.souravbose.blog.payloads.CategoryDto;
import com.souravbose.blog.repositories.CategoryRepo;
import com.souravbose.blog.services.CategoryService;

@Service
public class CategoryServiceImplementation implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId + ""));
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updatedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId + ""));
		
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId + ""));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> categoryDtos = categories.stream().
				map(category -> this.modelMapper.map(category, CategoryDto.class)).
				collect(Collectors.toList());
		
		return categoryDtos;
	}
}
