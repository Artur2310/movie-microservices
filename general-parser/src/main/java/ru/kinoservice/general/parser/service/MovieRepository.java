package ru.kinoservice.general.parser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kinoservice.general.parser.model.Movie;

@FeignClient(name="movie-data")
public interface MovieRepository {

    @PostMapping(value = "/add")
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie);

    @GetMapping(value = "/get-last-parsered")
    ResponseEntity<Integer> getLastParsered();
}
