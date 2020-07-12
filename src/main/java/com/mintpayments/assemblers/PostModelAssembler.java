package com.mintpayments.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mintpayments.controllers.PostController;
import com.mintpayments.models.Post;

@Component
public class PostModelAssembler extends RepresentationModelAssemblerSupport<Post, Post> {

	public PostModelAssembler() {
		super(PostController.class, Post.class);
	}

	@Override
	public Post toModel(Post post) {
		post.add(linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel(),
				linkTo(methodOn(PostController.class).enablePost(post.getId())).withRel("enable"),
				linkTo(methodOn(PostController.class).disablePost(post.getId())).withRel("disable"),
				linkTo(methodOn(PostController.class).getAllPosts()).withRel("posts"),
				linkTo(methodOn(PostController.class).getPageablePosts(null)).withRel("postsbypage"));

		return post;
	}

	@Override
	public CollectionModel<Post> toCollectionModel(Iterable<? extends Post> posts) {
		CollectionModel<Post> postModels = super.toCollectionModel(posts);

		postModels.add(linkTo(methodOn(PostController.class).getAllPosts()).withSelfRel(),
				linkTo(methodOn(PostController.class).getPageablePosts(null)).withRel("postsbypage"));

		return postModels;
	}
}
