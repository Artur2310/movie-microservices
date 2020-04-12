package ru.kinoservice.movie.parser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Movie {

    private Integer id;

    private String url;

    private int pictureId;

    private String title;

    private float imdb;

    private String description;

    private String genre;

    private Set<Integer> actors = new HashSet<Integer>();

}
