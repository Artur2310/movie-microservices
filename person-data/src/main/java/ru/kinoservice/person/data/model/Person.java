package ru.kinoservice.person.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Transient
    public static final String SEQUENCE_NAME = "person_sequence";

    @Id
    private Integer id;

    @Indexed(unique = true)
    private int sourceId;

    private String url;

    private String pictureUrl;

    private String name;

    private List<Integer> movies = new ArrayList<>();

}
