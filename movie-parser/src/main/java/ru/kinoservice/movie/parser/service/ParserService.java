package ru.kinoservice.movie.parser.service;

import ru.kinoservice.movie.parser.model.Movie;

import java.util.Optional;

public interface ParserService {

    Optional<Movie> parse(Integer id);
}
