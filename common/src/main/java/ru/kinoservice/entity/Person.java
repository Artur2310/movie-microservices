package ru.kinoservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @SequenceGenerator(name = "person_seq_id_generator", sequenceName = "person_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq_id_generator")
    private Integer id;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column
    private String url;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @ManyToMany(mappedBy = "people")
    private Set<Movie> movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(sourceId, person.sourceId) &&
                Objects.equals(url, person.url) &&
                Objects.equals(imageUrl, person.imageUrl) &&
                Objects.equals(name, person.name) &&
                Objects.equals(birthDate, person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, url, imageUrl, name, birthDate);
    }
}
