package ru.kinoservice.general.parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.general.parser.service.MovieParseStarter;

@RestController
public class ParserController {

    @Autowired
    MovieParseStarter movieParseStarter;

    @GetMapping(value = "/start")
    public ResponseEntity<?> startParse(){
        movieParseStarter.start();
        return ResponseEntity.noContent().build();
    }
}
