package ru.kinoservice.general.parser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kinoservice.general.parser.model.Movie;

@FeignClient(name="movie-parser")
public interface MovieParser {

    @GetMapping(value = "/parse/{id}")
    ResponseEntity<Movie> parseMoviePage(@PathVariable(value = "id") Integer id);
}
