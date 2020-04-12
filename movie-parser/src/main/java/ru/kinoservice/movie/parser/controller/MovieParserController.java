package ru.kinoservice.movie.parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.movie.parser.exception.ParseException;
import ru.kinoservice.movie.parser.model.Movie;
import ru.kinoservice.movie.parser.service.ParserService;

@RestController
@RequestMapping(value = "parse/{id}")
public class MovieParserController {

    @Autowired
    ParserService parserService;

    @GetMapping
    public ResponseEntity<Movie> parseMoviePage(@PathVariable Integer id) {
        return parserService.parse(id)
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseThrow(() -> new ParseException());
    }
}
