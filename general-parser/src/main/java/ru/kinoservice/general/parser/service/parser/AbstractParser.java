package ru.kinoservice.general.parser.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kinoservice.general.parser.config.ParserProperties;
import ru.kinoservice.general.parser.exception.PageAccessException;
import ru.kinoservice.general.parser.exception.ParseException;
import ru.kinoservice.general.parser.service.connection.ConnectionService;
import ru.kinoservice.general.parser.service.converter.Converter;
import ru.kinoservice.model.BaseParsedDto;

import java.io.IOException;

@Slf4j
public abstract class AbstractParser<T extends BaseParsedDto> {
    private final static String HTTPS = "https://";

    @Autowired
    private ParserProperties parserProperties;

    @Autowired
    private ConnectionService connectionService;


    public T parse(Integer id) {

        final String fullUrl = HTTPS + parserProperties.getUrl() + +'/' + getSection() + '/';

        try {
            Document doc = connectionService.getDocument(fullUrl);

            T item = getConverter().convertDocumentToDt(doc);
            item.setSourceId(id);
            item.setUrl(fullUrl);
            return item;

        } catch (IOException e) {
            log.error("Connection error url = {}", fullUrl, e);
            throw new PageAccessException(e);
        } catch (ParseException e) {
            log.error("Parse exception movie :" + id);
            throw e;
        }
    }

    protected abstract String getSection();

    protected abstract Converter<T> getConverter();
}
