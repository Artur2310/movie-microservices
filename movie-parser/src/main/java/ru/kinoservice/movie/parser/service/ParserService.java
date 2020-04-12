package ru.kinoservice.movie.parser.service;

import ru.kinoservice.movie.parser.model.Movie;

public interface ParserService {

    Movie parse(Integer id);
}
