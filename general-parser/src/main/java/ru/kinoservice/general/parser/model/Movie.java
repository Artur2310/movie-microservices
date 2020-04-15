package ru.kinoservice.general.parser.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    private Integer id;

    private Integer sourceId;

    private String url;

    private String imageUrl;

    private String title;

    private float imdb;

    private String description;

    private String genre;

}

