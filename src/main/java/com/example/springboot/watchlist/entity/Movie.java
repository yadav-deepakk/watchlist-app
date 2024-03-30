package com.example.springboot.watchlist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Please Enter Title")
	private String title;

	@NotNull(message = "Please Enter rating")
	@Min(message = "Please Enter rating above 0.0", value = 0)
	@Max(message = "Please Enter rating below 10.0", value = 10)
	private Float rating;

	@NotEmpty(message = "Priority can not be empty")
	private String priority;

	@Size(message = "Write comment between 4 to 50 characters.", max = 50, min = 4)
	private String comment;

	public Movie() {
		super();
	}

	public Movie(String title, Float rating, String priority, String comment) {
		super();
		this.title = title;
		this.rating = rating;
		this.priority = priority;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + "]";
	}
}
