package com.example.springboot.watchlist.pojo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidationLogic implements ConstraintValidator<PriorityValidator, String> {

	@Override
	public boolean isValid(String priorityValue, ConstraintValidatorContext context) {
		return priorityValue != null && priorityValue.trim().length() == 1
				&& "LMH".contains(priorityValue.trim().toUpperCase());
	}

}
