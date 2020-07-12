package com.mintpayments.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.mintpayments.BlogPrototypeApplication;
import com.mintpayments.models.Post;
import com.mintpayments.repositories.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogPrototypeApplication.class)
public class RepositoriesTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PostRepository postRepository;
	
	@Test
	@DirtiesContext
	public void updateTest() {
		Post post = postRepository.findById(2L).get();
		
		String challenge = "Test Title";
		
		post.setTitle(challenge);
		postRepository.save(post);
		
		
		post = postRepository.findById(2L).get();
		
		assertEquals(post.getTitle(), challenge);
	}
}