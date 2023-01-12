package com.souravbose.blog.services.ServiceImplementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.souravbose.blog.entities.Category;
import com.souravbose.blog.entities.Post;
import com.souravbose.blog.entities.User;
import com.souravbose.blog.exceptions.ResourceNotFoundException;
import com.souravbose.blog.payloads.PostDto;
import com.souravbose.blog.payloads.PostResponse;
import com.souravbose.blog.repositories.CategoryRepo;
import com.souravbose.blog.repositories.PostRepo;
import com.souravbose.blog.repositories.UserRepo;
import com.souravbose.blog.services.PostService;

@Service
public class PostServiceImplementation implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
		
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId + ""));
		
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId + ""));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setPostDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId + ""));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImage(postDto.getPostImage());
		
		Post updatePost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Long postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId + ""));
		
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> page = this.postRepo.findAll(pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId + "")); 
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId + ""));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Long userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId + ""));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByKeyword(keyword);
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
