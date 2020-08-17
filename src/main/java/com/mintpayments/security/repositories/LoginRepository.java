package com.mintpayments.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mintpayments.security.models.Login;

public interface LoginRepository extends JpaRepository<Login, Long>{
	Optional<Login> findByUserNameIgnoreCase(String userName);
}
