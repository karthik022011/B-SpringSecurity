package com.kartheek.springsecurity.validation;



import java.util.Arrays;
import java.util.List;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleTypeValidator implements ConstraintValidator<ValidateRoleType, String>{
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> roleTypes = Arrays.asList("ROLE_ADMIN, ROLE_USER");
        return roleTypes.contains(roleTypes);
    }
}

