package com.souravbose.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souravbose.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
