package ru.kinoservice.movie.parser.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.movie.parser.exception.PageAccessException;
import ru.kinoservice.movie.parser.exception.ParseException;
import ru.kinoservice.movie.parser.exception.ValidateUrlParameterException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(PageAccessException.class)
    ResponseEntity<ApiException> handlePageAccessException(){
        return new ResponseEntity<>(new ApiException("Access page error"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ParseException.class)
    ResponseEntity<ApiException> handleParseException(){
        return new ResponseEntity<>(new ApiException("Error during parsing"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateUrlParameterException.class)
    ResponseEntity<ApiException> handleValidateUrlParameterException(){
        return new ResponseEntity<>(new ApiException("Validate url exception"), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class ApiException {
        private String message;
    }
}
