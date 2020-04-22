package ru.kinoservice.general.parser.task;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.model.Movie;
import ru.kinoservice.general.parser.service.MovieParseStarter;
import ru.kinoservice.general.parser.service.MovieParser;
import ru.kinoservice.general.parser.service.MovieRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@Component
@Scope("prototype")
public class TaskParseMovie implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MovieParseStarter.class);

    private Integer numberMovie;
    private AtomicInteger countErrorParsing;
    private AtomicInteger threadLimit;

    private MovieRepository movieRepository;
    private MovieParser movieParser;

    @Autowired
    public TaskParseMovie(MovieRepository movieRepository, MovieParser movieParser){
        this.movieRepository = movieRepository;
        this.movieParser = movieParser;
    }

    @Override
    public void run() {
        try {
            ResponseEntity response = movieParser.parseMoviePage(numberMovie);
            if (response.getStatusCode() != HttpStatus.OK) return;
            movieRepository.addMovie((Movie) response.getBody());
            logger.info("Parse and Add success movie: " + numberMovie);
            countErrorParsing.set(0);

        } catch (FeignException.Forbidden e) {
            logger.error("Parse exception movie: " + numberMovie);
            countErrorParsing.incrementAndGet();
        } finally {
            threadLimit.incrementAndGet();
        }
    }
}
