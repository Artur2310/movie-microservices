package ru.kinoservice.general.parser.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.model.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Qualifier("movieParseStarter")
public class MovieParseStarter implements ParseStarter {

    private static Logger logger = LoggerFactory.getLogger(MovieParseStarter.class);

    private Integer countMovie = 0;
    AtomicBoolean terminated = new AtomicBoolean(false);

    @Autowired
    MovieParser movieParser;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void start() {
        logger.info("Start parse movies!");

        ExecutorService executor = Executors.newFixedThreadPool(20);
        long time = System.currentTimeMillis();

        while (countMovie < 1000) {
            Runnable task = new TaskParseMovie(countMovie++);
            executor.execute(task);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        time = System.currentTimeMillis() - time;
        logger.info("End parse movies! Time " + time/1000);

    }

    @Override
    public boolean isTerminated() {
        return terminated.get();
    }

    class TaskParseMovie implements Runnable {

        Integer numberMovie;

        private TaskParseMovie(Integer numberMovie) {
            this.numberMovie = numberMovie;
        }

        @Override
        public void run() {
            try {
                ResponseEntity response = movieParser.parseMoviePage(numberMovie);
                if (response.getStatusCode() != HttpStatus.OK) return;
                movieRepository.addMovie((Movie)response.getBody());
            } catch (FeignException.Forbidden e) {
                logger.error("Parse exception movie: " + numberMovie);

            }
        }
    }
}
