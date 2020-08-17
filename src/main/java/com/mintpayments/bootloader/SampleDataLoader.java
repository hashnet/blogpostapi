package com.mintpayments.bootloader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mintpayments.models.Post;
import com.mintpayments.models.User;
import com.mintpayments.repositories.PostRepository;
import com.mintpayments.repositories.UserRepository;
import com.mintpayments.security.models.Login;
import com.mintpayments.security.repositories.LoginRepository;

@Configuration
public class SampleDataLoader {
	private static Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner loadData() {
		return args -> {
			// Load user and post
			saveUserAndPost(
					new User("Henry", "Robert", "Admin", LocalDate.of(1992, 2, 10)), 
					new Post("My First Post", "This is my first post."),
					new Post("My Second Post", "This is my second post."),
					new Post("My Third Post", "This is my third post."));
			saveUserAndPost(
					new User("Moore Derek", "Sabah", "User", LocalDate.of(1984, 4, 21)), 
					new Post("My Fourth Post", "This is my fourth post."),
					new Post("My Fifth Post", "This is my fifth post."),
					new Post("My Sixth Post", "This is my sixth post."),
					new Post("My Seventh Post", "This is my seventh post."));
			saveUserAndPost(
					new User("Sheikh", "Hasan", "User", LocalDate.of(1989, 11, 03)), 
					new Post("My Eighth Post", "This is my eighth post."),
					new Post("My Ninth Post", "This is my ninth post."),
					new Post("My Tength Post", "This is my tength post."));
			
			//Load login details
			saveLogin(
					new Login("ROOT", passwordEncoder.encode("root"), "ADMIN,USER", true, true, true, true),
					new Login("USER1", passwordEncoder.encode("pass1"), "USER", true, true, true, true),
					new Login("USER2", passwordEncoder.encode("pass2"), "USER", true, true, true, true));
		};
	}
	
	private void saveUserAndPost(User user, Post... posts) {
		List<Post> postList = new ArrayList<>();
		for(Post post: posts) {
			post.setUser(user);
			postList.add(post);
		}
		user.setPosts(postList);
		
		logger.info("Preloading " + userRepository.save(user));
		postList.forEach(post -> {logger.info("Preloading " + postRepository.save(post));});
	}
	
	private void saveLogin(Login... logins) {
		for(Login login: logins) {
			logger.info("Preloading " + loginRepository.save(login));
		}
	}
}
