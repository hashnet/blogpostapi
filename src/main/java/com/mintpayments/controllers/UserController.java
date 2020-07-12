package com.mintpayments.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintpayments.assemblers.UserModelAssembler;
import com.mintpayments.exceptions.EntityNotFoundException;
import com.mintpayments.models.User;
import com.mintpayments.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserModelAssembler userModelAssembler;

	@GetMapping("/users")
	public ResponseEntity<CollectionModel<User>> getAllUsers() {
		
		return ResponseEntity.ok(
				userModelAssembler.toCollectionModel(repository.findAll()));
	
	}

	@PostMapping("/users")
	public ResponseEntity<User> newUser(@Valid @RequestBody User newUser) {
		User userModel = userModelAssembler.toModel(repository.save(newUser));

		return ResponseEntity
				.created(userModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(userModel);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return repository
				.findById(id)
				.map(userModelAssembler::toModel)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityNotFoundException(id));
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Long id) {
		User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

		if (newUser.getFirstNames() != null) {
			user.setFirstNames(newUser.getFirstNames());
		}

		if (newUser.getLastName() != null) {
			user.setLastName(newUser.getLastName());
		}
		
		if(newUser.getDateOfBirth() != null) {
			user.setDateOfBirth(newUser.getDateOfBirth());
		}

		return ResponseEntity.ok(userModelAssembler.toModel(user));
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
