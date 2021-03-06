package ru.kinoservice.person.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kinoservice.person.data.exception.PersonNotFoundException;
import ru.kinoservice.person.data.model.Person;
import ru.kinoservice.person.data.repository.PersonRepository;
import ru.kinoservice.person.data.service.SequenceGeneratorService;

import javax.validation.constraints.NotNull;

@RestController
public class CRUDController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SequenceGeneratorService sequenceService;

    @PostMapping(value = "/add")
    public ResponseEntity<Person> addPerson(@NotNull @RequestBody Person person) {
        person.setId(sequenceService.generateSequence(Person.SEQUENCE_NAME));
        return ResponseEntity.ok().body(personRepository.save(person));
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Person> addPerson(@PathVariable(value = "id") Integer id, @RequestBody Person person) {
        if (!personRepository.findById(id).isPresent()) {
            throw new PersonNotFoundException(id);
        }
        return ResponseEntity.ok().body(personRepository.save(person));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Person>> getPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(personRepository.findAll(pageable));
    }

    @GetMapping(value = "/get-by-id/{id}")
    public ResponseEntity<Person> getById(@PathVariable(value = "id") Integer id) {
        return personRepository.findById(id)
                .map(person -> ResponseEntity.ok().body(person))
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @GetMapping(value = "/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new PersonNotFoundException(id));

    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        personRepository.deleteAll();
        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok().body(personRepository.count());
    }

    @GetMapping(value = "/get-last-parsered")
    public ResponseEntity<Integer> getLastParsered() {
        return personRepository.findTopByOrderBySourceIdDesc()
                .map(person -> ResponseEntity.ok().body(person.getSourceId()))
                .orElseGet(() -> ResponseEntity.ok().body(0));
    }
}
