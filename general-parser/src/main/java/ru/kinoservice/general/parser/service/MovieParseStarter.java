package ru.kinoservice.general.parser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.service.starter.BaseParseStarter;
import ru.kinoservice.general.parser.task.TaskParseMovie;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("movieParseStarter")
@RefreshScope
public class MovieParseStarter extends BaseParseStarter {

    @Autowired
    private ApplicationContext context;

    @Override
    Runnable getTaskParse(Integer numberItem, AtomicInteger countError, AtomicInteger threadLimit) {
        TaskParseMovie taskParseMovie = (TaskParseMovie) context.getBean("taskParseMovie");
        taskParseMovie.setNumberMovie(numberItem);
        taskParseMovie.setCountErrorParsing(countError);
        taskParseMovie.setThreadLimit(threadLimit);
        return taskParseMovie;
    }

    @Override
    Integer getLastItem() {
        return repository.getLastParseredMovie().getBody() + 1;
    }
}
