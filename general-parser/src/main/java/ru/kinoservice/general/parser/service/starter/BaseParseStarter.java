package ru.kinoservice.general.parser.service.starter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseParseStarter implements ParseStarter {

    private AtomicInteger countErrorParsing = new AtomicInteger();
    private AtomicInteger threadLimit = new AtomicInteger(1000);

    @Override
    public void start(int lastItem) {
        ExecutorService executor = Executors.newFixedThreadPool(20);

        try {
            while (true) {
                threadLimit.decrementAndGet();
                Runnable task = getTaskParse(lastItem++, countErrorParsing, threadLimit);
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

}
