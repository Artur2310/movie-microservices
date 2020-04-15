package ru.kinoservice.movie.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.kinoservice.movie.data.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Integer> {
}
