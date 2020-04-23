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
import ru.kinoservice.general.parser.model.Person;
import ru.kinoservice.general.parser.service.CinemaRepository;
import ru.kinoservice.general.parser.service.MovieParseStarter;
import ru.kinoservice.general.parser.service.PersonParser;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@Component
@Scope("prototype")
public class TaskParsePerson implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MovieParseStarter.class);

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
            ResponseEntity response = personParser.parsePersonPage(number);
            if (response.getStatusCode() != HttpStatus.OK) return;
            cinemaRepository.addPerson((Person) response.getBody());
            logger.info("Parse and Add success person: " + number);
            countErrorParsing.set(0);

        } catch (FeignException.Forbidden e) {
            logger.error("Parse exception person: " + number);
            countErrorParsing.incrementAndGet();
        } finally {
            threadLimit.incrementAndGet();
        }
    }
}

