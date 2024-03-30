package com.example.springboot.watchlist.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingValidationLogic implements ConstraintValidator<RatingValidator, Float> {

	@Override
	public boolean isValid(Float ratingValue, ConstraintValidatorContext context) {
		return ratingValue != null && ratingValue > 0.0f && ratingValue < 10.0f;
	}

}
