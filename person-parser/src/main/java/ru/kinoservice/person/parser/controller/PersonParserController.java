package ru.kinoservice.person.parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.person.parser.model.Person;
import ru.kinoservice.person.parser.service.ParserService;

@RestController
@RequestMapping(value = "parse/{id}")
public class PersonParserController {

    @Autowired
    ParserService parserService;

    @GetMapping
    public ResponseEntity<Person> parsePersonPage(@PathVariable Integer id) {
        return ResponseEntity.ok().body(parserService.parse(id));
    }
}

