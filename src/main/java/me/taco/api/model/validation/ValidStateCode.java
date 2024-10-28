package me.taco.api.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = StateCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Custom validation annotation for state codes.
 * Use in String class fields (atributes) to validate state codes.
 */
public @interface ValidStateCode {
    
    String message() default "Invalid state code";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
