package ru.kinoservice.general.parser.service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("movieParseStarter")
@RefreshScope
public class MovieParseStarter implements ParseStarter {

    private static Logger logger = LoggerFactory.getLogger(MovieParseStarter.class);

    @Value("${parser,movies.thread.pool.size}")
    private Integer parserMoviesThreadPoolSize = 20;

    private AtomicInteger countErrorParsing = new AtomicInteger();

    private AtomicInteger threadLimit = new AtomicInteger(1000);

    @Autowired
    MovieParser movieParser;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void start() {

        int numberMovie = movieRepository.getLastParsered().getBody() + 1;
        ExecutorService executor = Executors.newFixedThreadPool(20);

        try {
            while (true) {
                threadLimit.decrementAndGet();
                Runnable task = new TaskParseMovie(numberMovie++, countErrorParsing, threadLimit);
                executor.execute(task);

                do {
                    if (countErrorParsing.get() > 1000) {
                        executor.shutdownNow();
                        return;
                    }
                } while (threadLimit.get() == 0);

            }
        } finally {
            executor.shutdown();
        }


    }


    @Data
    @AllArgsConstructor
    private class TaskParseMovie implements Runnable {

        private Integer numberMovie;
        private AtomicInteger countErrorParsing;
        private AtomicInteger threadLimit;


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
}
