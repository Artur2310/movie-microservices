package ru.kinoservice.movie.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    private Integer id;

    private Integer sourceId;

    private String url;

    private String imageUrl;

    private String title;

    private float imdb;

    private String description;

    private String genre;

}

