package ru.kinoservice.image.downloader.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.image.downloader.exception.ImageDownloadException;
import ru.kinoservice.image.downloader.exception.ValidateUrlException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ImageDownloadException.class)
    ResponseEntity<ApiException> handleImageDownloadException(){
        return new ResponseEntity<>(new ApiException("Error during download"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateUrlException.class)
    ResponseEntity<ApiException> handleValidateUrlException(){
        return new ResponseEntity<>(new ApiException("Incorrect param: url"), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class ApiException {
        private String message;
    }
}
