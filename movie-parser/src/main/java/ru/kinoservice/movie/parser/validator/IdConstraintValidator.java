package ru.kinoservice.movie.parser.validator;

import ru.kinoservice.movie.parser.annotation.IdConstraint;
import ru.kinoservice.movie.parser.exception.ValidateUrlParameterException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdConstraintValidator implements ConstraintValidator<IdConstraint, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value != null && value > 0){
            return true;
        } else {
            throw new ValidateUrlParameterException();
        }
    }

    @Override
    public void initialize(IdConstraint constraintAnnotation) {
    }
}
