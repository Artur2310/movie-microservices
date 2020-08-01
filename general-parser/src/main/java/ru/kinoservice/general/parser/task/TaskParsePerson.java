package ru.kinoservice.general.parser.task;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.service.CinemaRepository;
import ru.kinoservice.general.parser.service.parser.PersonParser;
import ru.kinoservice.model.PersonParsedDto;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@Component
@Scope("prototype")
@Slf4j
public class TaskParsePerson implements Runnable {

    private Integer number;
    private AtomicInteger countErrorParsing;
    private AtomicInteger threadLimit;

    private CinemaRepository cinemaRepository;
    private PersonParser personParser;

    @Autowired
    public TaskParsePerson(CinemaRepository cinemaRepository, PersonParser personParser){
        this.cinemaRepository = cinemaRepository;
        this.personParser = personParser;
    }

    @Override
    public void run() {
        try {
            PersonParsedDto personDto = personParser.parse(number);
            if (personDto == null) return;

            cinemaRepository.addPerson((Person) response.getBody());
            log.info("Parse and Add success person: " + number);
            countErrorParsing.set(0);

        } catch (Exception e) {
            log.error("Parse exception person: " + number);
            countErrorParsing.incrementAndGet();
        } finally {
            threadLimit.incrementAndGet();
        }
    }
}

