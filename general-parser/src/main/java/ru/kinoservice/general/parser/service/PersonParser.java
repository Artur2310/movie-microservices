package ru.kinoservice.general.parser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kinoservice.general.parser.model.Person;

@FeignClient(name="person-parser")
public interface PersonParser {

    @GetMapping(value = "/parse/{id}")
    ResponseEntity<Person> parsePersonPage(@PathVariable(value = "id") Integer id);
}
