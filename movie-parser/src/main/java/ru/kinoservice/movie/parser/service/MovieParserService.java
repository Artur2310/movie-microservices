package ru.kinoservice.movie.parser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kinoservice.movie.parser.model.Movie;

import java.util.Optional;

@Service
public class MovieParserService implements ParserService {

    @Value("${url}")
    private String url;

    @Override
    public Optional<Movie> parse(Integer id) {
        return Optional.empty();
    }
}
