package ru.kinoservice.person.parser.annotation;

import ru.kinoservice.person.parser.validator.IdConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdConstraintValidator.class)
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public @interface IdConstraint {
    String message() default "Incorrect param: url";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
