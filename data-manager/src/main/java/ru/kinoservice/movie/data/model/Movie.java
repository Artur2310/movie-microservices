package ru.kinoservice.movie.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Transient
    public static final String SEQUENCE_NAME = "movies_sequence";

    @Id
    private Integer id;

    @Indexed(unique = true)
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

