package ru.kinoservice.general.parser.service.converter;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import ru.kinoservice.general.parser.exception.ParseException;
import ru.kinoservice.model.PersonParsedDto;

import java.util.List;

@Component
@Slf4j
public class PersonConverter implements Converter<PersonParsedDto> {


    @Override
    public PersonParsedDto convertDocumentToDt(Document doc) {
        PersonParsedDto person = new PersonParsedDto();
        person.setName(parseName(doc));
        person.setImageUrl(parseImage(doc));
        return person;
    }


    private String parseImage(Document doc) {

        List<Element> elms = doc.select("div#right-panel img.right-pic");

        if (elms.isEmpty())
            return null;

        return elms.get(0).attr("src");
    }

    private String parseName(Document doc) {

        List<Element> elms = doc.select("div.content h1");

        if (!elms.isEmpty()) {
            return elms.get(0).getElementsByTag("h1").text();
        } else {
            log.error("Name person not found!");
            throw new ParseException();
        }

    }

}
