package ru.kinoservice.general.parser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kinoservice.general.parser.model.Movie;

@FeignClient(name="data-manager", qualifier = "movieRepository")
public interface MovieRepository extends CinemaRepository{

    @PostMapping(value = "/movie/add")
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie);

    @GetMapping(value = "/movie/get-last-parsered")
    ResponseEntity<Integer> getLastParsered();
}
