package ru.kinoservice.person.parser.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.person.parser.exception.PageAccessException;
import ru.kinoservice.person.parser.exception.ParseException;
import ru.kinoservice.person.parser.exception.TooManyConnectionsException;
import ru.kinoservice.person.parser.exception.ValidateUrlParameterException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(PageAccessException.class)
    ResponseEntity<ApiException> handlePageAccessException(PageAccessException e){
        return new ResponseEntity<>(new ApiException("Access page error", e.getStackTrace().toString()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ParseException.class)
    ResponseEntity<ApiException> handleParseException(ParseException e){
        return new ResponseEntity<>(new ApiException("Error during parsing", e.getStackTrace().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateUrlParameterException.class)
    ResponseEntity<ApiException> handleValidateUrlParameterException(){
        return new ResponseEntity<>(new ApiException("Validate url exception"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooManyConnectionsException.class)
    ResponseEntity<ApiException> handleTooManyConnectionsException(){
        return new ResponseEntity<>(new ApiException("Too many open connections"), HttpStatus.TOO_MANY_REQUESTS);
    }


    @Data
    @AllArgsConstructor
    private static class ApiException {
        private String message;
        private String stackTrace;

        public ApiException(String message){
            this.message = message;
        }
    }
}
