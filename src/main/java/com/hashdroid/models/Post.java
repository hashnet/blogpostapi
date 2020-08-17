package com.hashdroid.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@ApiModel(description="Post Entity")
public class Post extends RepresentationModel<Post>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(accessMode=AccessMode.READ_ONLY)
	private Long id;
	
	@NotBlank(message="Post title cannot be empty.")
	@Size(min=2, max = 140, message="Post title should be between 2 to 140 characters long.")
	@ApiModelProperty(notes = "Post title cannot be empty. Post title should be between 2 to 140 characters long.")
	private String title;
	
	private String text;
	
	private Boolean enabled;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(readOnly=true) 
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(readOnly=true)
	private LocalDateTime lastUpdatedDate;
	
	@ManyToOne
	@JsonIgnore
	private User user;

	public Post() {
		this.enabled = true;
	}
	
	public Post(String title, String text) {
		this.title = title;
		this.text = text;
		this.enabled = true;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", text=" + text + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", createdDate=" + createdDate + "]";
	}
	
}