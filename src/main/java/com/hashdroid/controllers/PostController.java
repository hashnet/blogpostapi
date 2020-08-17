package com.hashdroid.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hashdroid.assemblers.PostModelAssembler;
import com.hashdroid.exceptions.EntityNotFoundException;
import com.hashdroid.models.Post;
import com.hashdroid.repositories.PostRepository;

@RestController
@CrossOrigin(origins="*")
public class PostController {
	
	@Autowired
	private PostRepository repository;
	
	@Autowired
	private PostModelAssembler postModelAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Post> pagedResourcesAssembler;

	//Paginated
	@GetMapping("/postsbypage")
	public ResponseEntity<PagedModel<Post>> getPageablePosts(Pageable pageable) {

		Page<Post> postPages = repository.findAll(pageable);

		PagedModel<Post> pagedModel = pagedResourcesAssembler.toModel(postPages, postModelAssembler);

		return ResponseEntity.ok(pagedModel);

	}
	
	@GetMapping("/posts")
	public ResponseEntity<CollectionModel<Post>> getAllPosts() {
		
		return ResponseEntity.ok(
				postModelAssembler.toCollectionModel(repository.findAll()));
	
	}

	@PostMapping("/posts")
	public ResponseEntity<Post> newPost(@Valid @RequestBody Post newPost) {
		Post postModel = postModelAssembler.toModel(repository.save(newPost));

		return ResponseEntity
				.created(postModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(postModel);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable Long id) {
		return repository
				.findById(id)
				.map(postModelAssembler::toModel)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityNotFoundException(id));
	}
	
	
	@GetMapping("/users/{id}/posts")
	public ResponseEntity<CollectionModel<Post>> findEmployees(@PathVariable long id) {
		
		return ResponseEntity.ok(
			postModelAssembler.toCollectionModel(repository.findByUserId(id)));
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<?> updatePost(@RequestBody Post newPost, @PathVariable Long id) {
		Post post = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

		if (!post.isEnabled()) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
					.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
					.body(Problem.create().withTitle("Operation not allowed")
							.withDetail("Cannot update a post that is in disabled tatus"));
		}
		
		if (newPost.getTitle() != null) {
			post.setTitle(newPost.getTitle());
		}

		if (newPost.getText() != null) {
			post.setText(newPost.getText());
		}

		return ResponseEntity.ok(postModelAssembler.toModel(post));
	}
	
	@GetMapping("/posts/{id}/enable")
	public ResponseEntity<Post> enablePost(@PathVariable Long id) {
		 Post updatedPost = repository.findById(id).map(post -> {
			post.setEnabled(true);

			return repository.save(post);
		}).orElseThrow(() -> new EntityNotFoundException(id));
			
		return ResponseEntity.ok(postModelAssembler.toModel(updatedPost));
	}
	
	@GetMapping("/posts/{id}/disable")
	public ResponseEntity<Post> disablePost(@PathVariable Long id) {
		 Post updatedPost = repository.findById(id).map(post -> {
			post.setEnabled(false);

			return repository.save(post);
		}).orElseThrow(() -> new EntityNotFoundException(id));
			
		 return ResponseEntity.ok(postModelAssembler.toModel(updatedPost));
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
