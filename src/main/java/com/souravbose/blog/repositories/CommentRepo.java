package com.souravbose.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souravbose.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
