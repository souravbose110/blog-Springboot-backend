package com.souravbose.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.souravbose.blog.entities.Category;
import com.souravbose.blog.entities.Post;
import com.souravbose.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Long> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	
	// List<Post> findByPostTitleContaining(String postTitle) -> Hibernate Core 5.6.5 error
	
	@Query("select p from Post p where lower(p.postTitle) like concat('%', :keyword, '%')")
	List<Post> searchByKeyword(@Param("keyword") String postTitle);
}
