package com.hashdroid.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hashdroid.models.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByUserId(Long id);
}
