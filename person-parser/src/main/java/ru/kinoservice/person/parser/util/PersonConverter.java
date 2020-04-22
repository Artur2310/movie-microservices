package ru.kinoservice.person.parser.util;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kinoservice.person.parser.exception.ParseException;
import ru.kinoservice.person.parser.model.Person;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PersonConverter {

    private static Logger logger = LoggerFactory.getLogger(PersonConverter.class);

    public Person convertDocumentToPerson(Document doc) {
        Person person = new Person();
        person.setName(parseName(doc));
        person.setPictureUrl(parseImage(doc));
        person.setMovies(parseMovies(doc));
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
            logger.error("Name person not found!");
            throw new ParseException();
        }

    }

    private List<Integer> parseMovies(Document doc) {

        List<Element> elms = doc.select("div[class=movie tiny-movie]");

        return elms.stream()
                .flatMap(person -> person.attributes().asList().stream())
                .filter(movie -> movie.getKey().equals("id"))
                .map(Attribute::getValue)
                .map(idMovie -> {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(idMovie);
                    m.find();
                    return m.group();
                })
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
