package com.mintpayments.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@ApiModel(description="User  Entity")
public class User extends RepresentationModel<User>{
	public enum Type {
		Admin,
		User
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(accessMode=AccessMode.READ_ONLY)
	private Long id;
	
	private String firstNames;
	
	@NotBlank(message="User lastName cannot be empty.")
	@Size(min=2, max = 140, message="User lastName should be between 2 to 140 characters long.")
	@ApiModelProperty(notes = "User lastName cannot be empty. User lastName should be between 2 to 140 characters long.")
	private String lastName;
	
	@NotNull(message="Type must be provided [Adming,User]")
	private Type type;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(readOnly=true) 
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(readOnly=true)
	private LocalDateTime lastUpdatedDate;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Post> posts;

	public User() {
	}
	
	public User(String firstNames, String lastName, String type, LocalDate dateOfBirth) {
		this.firstNames = firstNames;
		this.lastName = lastName;
		this.type = Type.valueOf(type);
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> post) {
		this.posts = post;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstNames=" + firstNames + ", lastName=" + lastName + ", type=" + type
				+ ", dateOfBirth=" + dateOfBirth + ", createdDate=" + createdDate + ", lastUpdatedDate="
				+ lastUpdatedDate + "]";
	}
	
	
}