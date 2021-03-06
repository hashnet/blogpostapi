package com.hashdroid.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.hashdroid.repositories.PostRepository;

/**
 * 
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PostRepository repository;

	@Test
	public void getShouldFetchAHalDocument() throws Exception {

/*		given(repository.findAll()).willReturn( //
				Arrays.asList( //
						new Post(1L, "Frodo", "Baggins", "ring bearer"), new Post(2L, "Bilbo", "Baggins", "burglar")));

		mvc.perform(get("/posts").accept(MediaTypes.HAL_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$._embedded.POSTs[0].id", is(1)))
				.andExpect(jsonPath("$._embedded.POSTs[0].firstName", is("Frodo")))
				.andExpect(jsonPath("$._embedded.POSTs[0].lastName", is("Baggins")))
				.andExpect(jsonPath("$._embedded.POSTs[0].role", is("ring bearer")))
				.andExpect(jsonPath("$._embedded.POSTs[0]._links.self.href", is("http://localhost/POSTs/1")))
				.andExpect(jsonPath("$._embedded.POSTs[0]._links.POSTs.href", is("http://localhost/POSTs")))
				.andExpect(jsonPath("$._embedded.POSTs[1].id", is(2)))
				.andExpect(jsonPath("$._embedded.POSTs[1].firstName", is("Bilbo")))
				.andExpect(jsonPath("$._embedded.POSTs[1].lastName", is("Baggins")))
				.andExpect(jsonPath("$._embedded.POSTs[1].role", is("burglar")))
				.andExpect(jsonPath("$._embedded.POSTs[1]._links.self.href", is("http://localhost/POSTs/2")))
				.andExpect(jsonPath("$._embedded.POSTs[1]._links.POSTs.href", is("http://localhost/POSTs")))
				.andExpect(jsonPath("$._links.self.href", is("http://localhost/POSTs")))
				.andReturn(); */
	}
}