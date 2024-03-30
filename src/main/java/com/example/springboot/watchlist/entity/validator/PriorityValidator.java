package com.example.springboot.watchlist.entity.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriorityValidationLogic.class)
public @interface PriorityValidator {

	String message() default "Please enter L, M, H only";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
