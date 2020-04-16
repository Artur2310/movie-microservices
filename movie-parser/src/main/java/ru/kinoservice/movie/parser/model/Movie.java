package ru.kinoservice.movie.parser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Movie {

    private Integer sourceId;

    private String url;

    private String imageUrl;

    private String title;

    private float imdb;

    private String description;

    private String genres;

    private String countries;

    private Date date;

}
