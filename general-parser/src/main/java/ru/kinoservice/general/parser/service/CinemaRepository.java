package ru.kinoservice.general.parser.service;

import org.springframework.http.ResponseEntity;

public interface CinemaRepository {

    ResponseEntity<Integer> getLastParsered();
}
