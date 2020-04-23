package ru.kinoservice.general.parser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseParseStarter implements ParseStarter {
    private static Logger logger = LoggerFactory.getLogger(MovieParseStarter.class);

    @Value("${parser.thread.pool.size}")
    private Integer parserThreadPoolSize = 20;

    private AtomicInteger countErrorParsing = new AtomicInteger();
    private AtomicInteger threadLimit = new AtomicInteger(1000);

    @Autowired
    protected CinemaRepository repository;

    @Override
    public void start() {
        int number = getLastItem();
        ExecutorService executor = Executors.newFixedThreadPool(20);

        try {
            while (true) {
                threadLimit.decrementAndGet();
                Runnable task = getTaskParse(number++, countErrorParsing, threadLimit);
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

    abstract Runnable getTaskParse(Integer countItem, AtomicInteger countError, AtomicInteger threadLimit);

    abstract Integer getLastItem();
}
