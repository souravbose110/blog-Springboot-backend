package com.souravbose.blog.services.ServiceImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souravbose.blog.entities.Comment;
import com.souravbose.blog.entities.Post;
import com.souravbose.blog.exceptions.ResourceNotFoundException;
import com.souravbose.blog.payloads.CommentDto;
import com.souravbose.blog.repositories.CommentRepo;
import com.souravbose.blog.repositories.PostRepo;
import com.souravbose.blog.services.CommentService;

@Service
public class CommentServiceImplementation implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId + ""));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment createComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(createComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		Comment comment = this.commentRepo.findById(commentId).
				orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId + ""));
		
		this.commentRepo.delete(comment);
	}

}
