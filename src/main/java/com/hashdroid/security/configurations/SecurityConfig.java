package com.hashdroid.security.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.mvcMatchers("/users").hasAuthority("ADMIN")
		.mvcMatchers("/posts").hasAnyAuthority("USER", "ADMIN")
		.mvcMatchers("/welcome").permitAll()
		.antMatchers("/**").permitAll()
		.antMatchers("/h2-console/**").permitAll()				// Takes /h2-console out of Spring Security’s authorization
//      .and().csrf().ignoringAntMatchers("/h2-console/**")		// Turns off CSRF only for /h2-console.
        .and()
        .headers().frameOptions().sameOrigin()			// Enables loading pages in frames as long as the frames come from our own site
		.and()
		.httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	// To ensure that no session is used and every client request will go through authentication phase

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}