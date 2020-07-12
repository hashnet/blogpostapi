package com.mintpayments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mintpayments.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
