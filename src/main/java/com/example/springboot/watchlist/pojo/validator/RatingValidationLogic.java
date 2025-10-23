package com.example.springboot.watchlist.pojo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidationLogic implements ConstraintValidator<MovieRatingValidator, Float> {

	@Override
	public boolean isValid(Float ratingValue, ConstraintValidatorContext context) {
		return ratingValue != null && ratingValue > 0.0f && ratingValue < 10.0f;
	}

}
