package ru.kinoservice.general.parser.service.parser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kinoservice.general.parser.service.converter.Converter;
import ru.kinoservice.general.parser.service.converter.PersonConverter;
import ru.kinoservice.model.PersonParsedDto;

@Service
@AllArgsConstructor
public class PersonParser extends AbstractParser<PersonParsedDto> {
    public static String SECTION = "people";

    private final PersonConverter converter;

    @Override
    protected String getSection() {
        return SECTION;
    }

    @Override
    protected Converter getConverter() {
        return converter;
    }

}

