package ru.kinoservice.general.parser.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.general.parser.service.GeneralParseStarter;

@RestController
@AllArgsConstructor
public class ParserController {

    @Qualifier("generalParseStarter")
    private final GeneralParseStarter generalParseStarter;

    @GetMapping(value = "/start")
    public ResponseEntity<?> startParse(){
        generalParseStarter.start();
        return ResponseEntity.noContent().build();
    }
}
