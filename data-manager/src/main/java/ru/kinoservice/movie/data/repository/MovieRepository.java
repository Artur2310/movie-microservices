package ru.kinoservice.movie.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kinoservice.movie.data.model.Movie;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Integer> {

    Optional<Movie> findTopByOrderBySourceIdDesc();
}
