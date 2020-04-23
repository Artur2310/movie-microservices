package ru.kinoservice.general.parser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kinoservice.general.parser.model.Movie;
import ru.kinoservice.general.parser.model.Person;

@FeignClient(name="data-manager")
public interface CinemaRepository {

    @PostMapping(value = "/movie/add")
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie);

    @GetMapping(value = "/movie/get-last-parsered")
    ResponseEntity<Integer> getLastParseredMovie();

    @PostMapping(value = "/person/add")
    ResponseEntity<Person> addPerson(@RequestBody Person person);

    @GetMapping(value = "/person/get-last-parsered")
    ResponseEntity<Integer> getLastParseredPerson();
}
