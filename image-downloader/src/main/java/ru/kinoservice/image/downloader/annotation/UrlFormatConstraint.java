package ru.kinoservice.image.downloader.annotation;

import ru.kinoservice.image.downloader.validator.UrlFormatConstrainValidator;

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
@Constraint(validatedBy = UrlFormatConstrainValidator.class)
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public @interface UrlFormatConstraint {
    String message() default "Incorrect param: url";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
