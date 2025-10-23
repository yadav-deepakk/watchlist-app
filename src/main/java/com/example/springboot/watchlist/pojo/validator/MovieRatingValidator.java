package com.example.springboot.watchlist.pojo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidationLogic.class)
public @interface MovieRatingValidator {
	String message() default "Please enter rating between 0.0 and 10.0";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
