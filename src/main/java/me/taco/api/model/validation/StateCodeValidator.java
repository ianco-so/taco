package me.taco.api.model.validation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.taco.api.model.enums.StateCode;

public class StateCodeValidator implements ConstraintValidator<ValidStateCode, String> {

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return Arrays.stream(StateCode.values())
            .anyMatch(state -> state.name().equals(code.toUpperCase()));
    }

}
