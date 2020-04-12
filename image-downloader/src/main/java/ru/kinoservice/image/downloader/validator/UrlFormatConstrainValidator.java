package ru.kinoservice.image.downloader.validator;


import org.assertj.core.util.Strings;
import ru.kinoservice.image.downloader.Property;
import ru.kinoservice.image.downloader.annotation.UrlFormatConstraint;
import ru.kinoservice.image.downloader.exception.ValidateUrlException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlFormatConstrainValidator implements ConstraintValidator<UrlFormatConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Strings.isNullOrEmpty(value)
                && Property.formatImages.stream()
                .anyMatch(format -> value.toLowerCase().endsWith(format))) {
            return true;
        } else {
            throw new ValidateUrlException();
        }
    }

    @Override
    public void initialize(UrlFormatConstraint constraintAnnotation) {
    }
}
