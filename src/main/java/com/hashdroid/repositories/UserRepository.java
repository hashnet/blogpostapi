package com.hashdroid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hashdroid.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
