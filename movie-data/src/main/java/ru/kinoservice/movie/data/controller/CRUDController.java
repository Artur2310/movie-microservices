package ru.kinoservice.movie.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kinoservice.movie.data.exception.MovieNotFoundException;
import ru.kinoservice.movie.data.model.Movie;
import ru.kinoservice.movie.data.repository.MovieRepository;

@RestController
public class CRUDController {

    @Autowired
    MovieRepository movieRepository;

    @PostMapping(value = "/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok().body(movieRepository.save(movie));
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Movie> addMovie(@PathVariable(value = "id") Integer id, @RequestBody Movie movie) {
        if(!movieRepository.findById(id).isPresent()){
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

    @GetMapping(value = "/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new MovieNotFoundException(id));

    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> getCount(){
        return ResponseEntity.ok().body(movieRepository.count());
    }
}
