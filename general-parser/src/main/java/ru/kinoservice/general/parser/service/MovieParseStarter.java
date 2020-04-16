package ru.kinoservice.general.parser.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.model.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Qualifier("movieParseStarter")
@RefreshScope
public class MovieParseStarter implements ParseStarter {

    private static Logger logger = LoggerFactory.getLogger(MovieParseStarter.class);

    @Value("${parser,movies.thread.pool.size}")
    private Integer parserMoviesThreadPoolSize = 20;
    @Value("${parser,movies.count}")
    private Integer countMovies = 1000;

    @Autowired
    MovieParser movieParser;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void start() {

        int numberMovie = movieRepository.getLastParsered().getBody() + 1;
        ExecutorService executor = Executors.newFixedThreadPool(20);

        while (numberMovie < 1000) {
            Runnable task = new TaskParseMovie(numberMovie++);
            executor.execute(task);
        }
        executor.shutdown();

        while (!executor.isTerminated()) {
        }

    }


    private class TaskParseMovie implements Runnable {

        private Integer numberMovie;

        private TaskParseMovie(Integer numberMovie) {
            this.numberMovie = numberMovie;
        }

        @Override
        public void run() {
            try {
                ResponseEntity response = movieParser.parseMoviePage(numberMovie);
                if (response.getStatusCode() != HttpStatus.OK) return;
                movieRepository.addMovie((Movie) response.getBody());

            } catch (FeignException.Forbidden e) {
                logger.error("Parse exception movie: " + numberMovie);
            }
        }
    }
}
