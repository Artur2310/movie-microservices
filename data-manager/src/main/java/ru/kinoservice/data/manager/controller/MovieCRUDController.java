package ru.kinoservice.data.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kinoservice.data.manager.exception.MovieNotFoundException;
import ru.kinoservice.data.manager.model.Movie;
import ru.kinoservice.data.manager.repository.MovieRepository;
import ru.kinoservice.data.manager.service.SequenceGeneratorService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/movie")
public class MovieCRUDController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    SequenceGeneratorService sequenceService;

    @PostMapping(value = "/add")
    public ResponseEntity<Movie> addMovie(@NotNull @RequestBody Movie movie) {
        movie.setId(sequenceService.generateSequence(Movie.SEQUENCE_NAME));
        return ResponseEntity.ok().body(movieRepository.save(movie));
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Movie> addMovie(@PathVariable(value = "id") Integer id, @RequestBody Movie movie) {
        if (!movieRepository.findById(id).isPresent()) {
            throw new MovieNotFoundException(id);
        }
        return ResponseEntity.ok().body(movieRepository.save(movie));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Movie>> getPage(Pageable pageable) {
        return ResponseEntity.ok().body(movieRepository.findAll(pageable));
    }

    @GetMapping(value = "/get-by-id/{id}")
    public ResponseEntity<Movie> getById(@PathVariable(value = "id") Integer id) {
        return movieRepository.findById(id)
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @GetMapping(value = "/get-by-source-id/{id}")
    public ResponseEntity<Movie> getBySourceId(@PathVariable(value = "id") Integer id) {
        return movieRepository.findMovieBySourceId(id)
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @GetMapping(value = "/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new MovieNotFoundException(id));

    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        movieRepository.deleteAll();
        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok().body(movieRepository.count());
    }

    @GetMapping(value = "/get-last-parsered")
    public ResponseEntity<Integer> getLastParsered() {
        return movieRepository.findTopByOrderBySourceIdDesc()
                .map(movie -> ResponseEntity.ok().body(movie.getSourceId()))
                .orElseGet(() -> ResponseEntity.ok().body(0));
    }
}
