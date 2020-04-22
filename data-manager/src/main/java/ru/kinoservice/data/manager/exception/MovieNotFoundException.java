package ru.kinoservice.data.manager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieNotFoundException extends RuntimeException {

    Integer id;
}
