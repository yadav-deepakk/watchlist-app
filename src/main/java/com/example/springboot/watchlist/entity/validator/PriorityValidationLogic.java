package com.example.springboot.watchlist.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriorityValidationLogic implements ConstraintValidator<PriorityValidator, String> {

	@Override
	public boolean isValid(String priorityValue, ConstraintValidatorContext context) {
		return priorityValue != null && priorityValue.trim().length() == 1
				&& "LMH".contains(priorityValue.trim().toUpperCase());
	}

}
