package ru.kinoservice.general.parser.service.parser;

import ru.kinoservice.model.PersonParsedDto;

public interface ParserService {

    PersonParsedDto parse(Integer id);
}
