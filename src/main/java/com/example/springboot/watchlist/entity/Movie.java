package com.example.springboot.watchlist.entity;

import org.hibernate.annotations.UuidGenerator;

import com.example.springboot.watchlist.pojo.validator.MovieRatingValidator;
import com.example.springboot.watchlist.pojo.validator.PriorityValidator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public @Entity class Movie {

	@Id
	@UuidGenerator
	private String id;

	@NotEmpty(message = "Please Enter Title")
	private String title;

	@MovieRatingValidator
	private Float rating;

	@PriorityValidator
	private String priority;

	@Size(message = "Write comment between 4 to 50 characters.", max = 50, min = 4)
	private String comment;

}
