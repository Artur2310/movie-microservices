package ru.kinoservice.data.manager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.kinoservice.data.manager.model.Movie;

import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Integer> {

    Optional<Movie> findTopByOrderBySourceIdDesc();

    Optional<Movie> findMovieBySourceId(Integer id);
}
