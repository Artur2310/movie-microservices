package ru.kinoservice.movie.parser.service;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kinoservice.movie.parser.exception.PageAccessException;
import ru.kinoservice.movie.parser.exception.ParseException;
import ru.kinoservice.movie.parser.model.Movie;
import ru.kinoservice.movie.parser.util.MovieConverter;

import java.io.IOException;

@Service
public class MovieParserService implements ParserService {

    private static Logger logger = LoggerFactory.getLogger(MovieParserService.class);

    @Autowired
    ConnectionService connectionService;

    @Autowired
    MovieConverter converter;

    @Value("${parse.url}")
    private String url;

    @Override
    public Movie parse(Integer id) {

        final String fullUrl = url + id;

        try {
            Document doc = connectionService.getDocument(fullUrl);

            Movie movie = converter.convertDocumentToMovie(doc);
            movie.setId(id);
            movie.setUrl(fullUrl);
            return movie;

        } catch (IOException e) {
            logger.error("Connection error :" + fullUrl, e);
            throw new PageAccessException(e);
        } catch (ParseException e) {
            logger.error("Parse exception movie :" + id);
            throw e;
        }
    }

}
