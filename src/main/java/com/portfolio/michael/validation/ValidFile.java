package com.portfolio.michael.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file";

    String[] allowedTypes() default {}; // MIME types

    String[] allowedExtensions() default {}; // Optional

    long maxSize() default -1; // e.g. 2MB = 2 * 1024 * 1024

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
