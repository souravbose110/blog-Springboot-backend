package com.souravbose.blog.services;

import java.util.List;

import com.souravbose.blog.payloads.PostDto;
import com.souravbose.blog.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Long userId, Long categoryId);
	
	PostDto updatePost(PostDto postDto, Long postId);
	
	void deletePost(Long postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(Long postId);
	
	List<PostDto> getPostByCategory(Long categoryId);
	
	List<PostDto> getPostByUser(Long userId);
	
	List<PostDto> searchPosts(String keyword);	
}
