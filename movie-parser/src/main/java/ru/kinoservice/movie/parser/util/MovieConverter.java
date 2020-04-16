package ru.kinoservice.movie.parser.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kinoservice.movie.parser.exception.ParseException;
import ru.kinoservice.movie.parser.model.Movie;
import ru.kinoservice.movie.parser.service.MovieParserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class MovieConverter {

    private static Logger logger = LoggerFactory.getLogger(MovieParserService.class);

    public Movie convertDocumentToMovie(Document doc) {
        Movie movie = new Movie();
        movie.setTitle(parseTitle(doc));
        movie.setDescription(parseDescription(doc));
        movie.setImdb(parseIMDB(doc));
        movie.setImageUrl(parseImage(doc));
        movie.setGenres(parseGenre(doc));
        movie.setCountries(parseCountries(doc));
        movie.setDate(parseDate(doc));
        return movie;
    }

    private String parseTitle(Document doc) {

        List<Element> elms = doc.select("h1[itemprop=name]");

        if (!elms.isEmpty()) {
            return elms.get(0).text();
        } else {
            logger.error("Movie title not found!");
            throw new ParseException();
        }
    }

    private Float parseIMDB(Document doc) {

        List<Element> elms = doc.select("div[class=block view] div[style=overflow:hidden;] ul li ");
        float imdb;

        if (!elms.isEmpty()) {

            String imdbStr;
            if (elms.size() < 7) {
                imdbStr = elms.get(5).text();
            } else {
                imdbStr = elms.get(6).text();
            }

            Pattern p = Pattern.compile("[0-9].[0-9]");
            Matcher m = p.matcher(imdbStr);

            if (m.find()) {
                imdbStr = imdbStr.substring(m.start(), m.end());

                try {
                    imdb = Float.parseFloat(imdbStr);
                } catch (NumberFormatException e) {
                    return 0f;
                }
                return imdb;
            }
        }

        logger.debug("Imdb not found!");
        return 0f;
    }

    private String parseDescription(Document doc) {

        List<Element> elms = doc.select("span[itemprop=description] p");

        if (!elms.isEmpty()) {

            String description = elms.get(0).text();
            if (description.length() > 549)
                description = description.substring(0, 549);

            return description;
        } else {
            logger.error("Description not found");
            throw new ParseException();
        }
    }

    private String parseGenre(Document doc) {

        List<Element> elms = doc.select("div[class=block view] ul li a");

        return elms.stream().filter(e -> e.toString().contains("category_id")).map(e -> e.text()).collect(Collectors.joining(", "));
    }

    private String parseImage(Document doc) {

        List<Element> elms = doc.select("div[class=main-pic] img");

        if (elms.isEmpty())
            return null;

        return elms.get(0).attr("src");
    }

    private String parseCountries(Document doc) {
        List<Element> elms = doc.select("div[class=block view] ul li a");

        return elms.stream()
                .filter(e -> e.toString().contains("country_id"))
                .map(e -> e.text())
                .collect(Collectors.joining(", "));
    }

    private Date parseDate(Document doc) {
        List<Element> elms = doc.select("div[class=block view] ul li span");

        return elms.stream()
                .filter(e -> e.parent().text().contains("Дата выпуска"))
                .map(e -> {
                    try {
                        return new SimpleDateFormat("dd MMM yyyy").parse(e.text());
                    } catch (java.text.ParseException ex) {
                        logger.error("Error parse date movie! ");
                        throw new ParseException();
                    }
                })
                .findFirst().get();
    }
}
