package ru.kinoservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movie")
@Data
public class Movie {

    private static final long serialVersionUID = -3594924397715574143L;

    @Id
    @SequenceGenerator(name = "movie_seq_id_generator", sequenceName = "movie_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq_id_generator")
    private Integer id;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column
    private String url;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String title;

    @Column
    private Float imdb;

    @Column
    private String description;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "movie_country",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "country_id")}
    )
    private Set<Country> countries;

    @ManyToMany
    @JoinTable(
            name = "movie_person",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")}
    )
    private Set<Person> people;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(sourceId, movie.sourceId) &&
                Objects.equals(url, movie.url) &&
                Objects.equals(imageUrl, movie.imageUrl) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(imdb, movie.imdb) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(releaseDate, movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, url, imageUrl, title, imdb, description, releaseDate);
    }
}
