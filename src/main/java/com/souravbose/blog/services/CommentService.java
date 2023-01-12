package com.souravbose.blog.services;

import com.souravbose.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Long postId);
	
	void deleteComment(Long commentId);
}
