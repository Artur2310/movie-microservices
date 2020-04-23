package ru.kinoservice.general.parser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.task.TaskParsePerson;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("personParseStarter")
@RefreshScope
public class PersonParseStarter extends BaseParseStarter{

    @Autowired
    private ApplicationContext context;

    @Override
    Runnable getTaskParse(Integer numberItem, AtomicInteger countError, AtomicInteger threadLimit) {
        TaskParsePerson taskParsePerson = (TaskParsePerson) context.getBean("taskParsePerson");
        taskParsePerson.setNumber(numberItem);
        taskParsePerson.setCountErrorParsing(countError);
        taskParsePerson.setThreadLimit(threadLimit);
        return taskParsePerson;
    }

    @Override
    Integer getLastItem() {
        return repository.getLastParseredPerson().getBody() + 1;
    }

}
