package ru.kinoservice.general.parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.general.parser.service.GeneralParseStarter;

@RestController
public class ParserController {

    @Autowired
    @Qualifier("generalParseStarter")
    GeneralParseStarter generalParseStarter;

    @GetMapping(value = "/start")
    public ResponseEntity<?> startParse(){
        generalParseStarter.start();
        return ResponseEntity.noContent().build();
    }
}
