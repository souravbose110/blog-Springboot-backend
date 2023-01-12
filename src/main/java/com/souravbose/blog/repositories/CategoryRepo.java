package com.souravbose.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souravbose.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	
}
