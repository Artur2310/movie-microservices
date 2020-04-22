package ru.kinoservice.person.parser.service;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kinoservice.person.parser.exception.PageAccessException;
import ru.kinoservice.person.parser.exception.ParseException;
import ru.kinoservice.person.parser.model.Person;
import ru.kinoservice.person.parser.util.PersonConverter;

import java.io.IOException;

@Service
public class PersonParserService implements ParserService {

    private static Logger logger = LoggerFactory.getLogger(PersonParserService.class);

    @Autowired
    ConnectionService connectionService;

    @Autowired
    PersonConverter converter;

    @Value("${parse.url}")
    private String url;

    @Override
    public Person parse(Integer id) {

        final String fullUrl = url + id;

        try {
            Document doc = connectionService.getDocument(fullUrl);

            Person person = converter.convertDocumentToPerson(doc);
            person.setSourceId(id);
            person.setUrl(fullUrl);
            return person;

        } catch (IOException e) {
            logger.error("Connection error :" + fullUrl, e);
            throw new PageAccessException(e);
        } catch (ParseException e) {
            logger.error("Parse exception movie :" + id);
            throw e;
        }
    }

}

