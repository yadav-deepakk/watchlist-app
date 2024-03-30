package com.example.springboot.watchlist.entity.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidationLogic.class)
public @interface RatingValidator {

	String message() default "Please enter rating between 0.0 and 10.0";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
