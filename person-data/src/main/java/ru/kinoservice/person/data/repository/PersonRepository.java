package ru.kinoservice.person.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.kinoservice.person.data.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<Person, Integer> {

    Optional<Person> findTopByOrderBySourceIdDesc();
}
