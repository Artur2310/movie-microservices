package ru.kinoservice.person.parser.service;

import ru.kinoservice.person.parser.model.Person;

public interface ParserService {

    Person parse(Integer id);
}
