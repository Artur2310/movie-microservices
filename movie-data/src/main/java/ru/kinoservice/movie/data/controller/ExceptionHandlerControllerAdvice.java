package ru.kinoservice.movie.data.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.movie.data.exception.MovieNotFoundException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(MovieNotFoundException.class)
    ResponseEntity<ApiException> handleMovieNotFoundException(MovieNotFoundException e){
        return new ResponseEntity<>(new ApiException("Movie "+ String.valueOf(e.getId()) +" not found "), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class ApiException {
        private String message;

    }
}
