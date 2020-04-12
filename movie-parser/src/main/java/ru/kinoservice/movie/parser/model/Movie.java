package ru.kinoservice.movie.parser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Movie {

    private Integer id;

    private String url;

    private String imageUrl;

    private String title;

    private float imdb;

    private String description;

    private String genre;

}
