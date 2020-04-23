package ru.kinoservice.general.parser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
@Qualifier("generalParseStarter")
public class GeneralParseStarter implements ParseStarter {

    @Autowired
    @Qualifier("movieParseStarter")
    private ParseStarter movieParseStarter;

    @Autowired
    @Qualifier("personParseStarter")
    private ParseStarter personParseStarter;

    @Autowired
    private ReentrantLock parseLock;

    public void start() {
        if (parseLock.tryLock()) {
            parse();
        }
    }

    private void parse() {
        try {
            parseLock.lock();
            movieParseStarter.start();
            personParseStarter.start();
        } finally {
            parseLock.unlock();
        }
    }
}
