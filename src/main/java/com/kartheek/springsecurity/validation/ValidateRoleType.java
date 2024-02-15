package com.kartheek.springsecurity.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RoleTypeValidator.class)
public @interface  ValidateRoleType {
    String message() default "Invalid Role Type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


