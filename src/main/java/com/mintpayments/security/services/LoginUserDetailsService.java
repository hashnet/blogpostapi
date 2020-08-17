package com.mintpayments.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mintpayments.security.models.Login;
import com.mintpayments.security.repositories.LoginRepository;
import com.mintpayments.security.representationmodels.AuthUserDetails;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Optional<Login> login = loginRepository.findByUserNameIgnoreCase(userName);
		
		return login.map(AuthUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Login user: " + userName + " not found."));
	}

}
