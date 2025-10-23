package com.example.springboot.watchlist.pojo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriorityValidationLogic.class)
public @interface PriorityValidator {

	String message() default "Please enter L, M, H only";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
