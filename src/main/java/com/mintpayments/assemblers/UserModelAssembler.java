package com.mintpayments.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mintpayments.controllers.UserController;
import com.mintpayments.models.User;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, User> {

	@Autowired
	PostModelAssembler postModelAssembler;
	
	public UserModelAssembler() {
		super(UserController.class, User.class);
	}

	@Override
	public User toModel(User user) {
		user.setPosts(user.getPosts().stream().map(postModelAssembler::toModel).collect(Collectors.toList()));
		
		user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel())
				.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));

		return user;
	}
	
	@Override
	public CollectionModel<User> toCollectionModel(Iterable<? extends User> users) 
	{
		CollectionModel<User> userModels = super.toCollectionModel(users);
		
		userModels.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
		
		return userModels;
	}
}
