package ru.kinoservice.general.parser.service.converter;

import org.jsoup.nodes.Document;
import ru.kinoservice.model.BaseParsedDto;

public interface Converter<T extends BaseParsedDto> {

    T convertDocumentToDt(Document doc);
}
