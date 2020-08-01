package ru.kinoservice.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Dto for add movie in database")
public class MovieParsedDto extends BaseParsedDto{

    private String title;

    private Float imdb;

    private String description;

    private Date releaseDate;

    private Set<String> genres;

    private Set<String> countries;

    private Set<Integer> actors;

    private Set<Integer> directors;
}
