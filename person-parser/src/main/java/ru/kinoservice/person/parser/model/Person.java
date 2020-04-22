package ru.kinoservice.person.parser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Person {

    private int sourceId;

    private String url;

    private String pictureUrl;

    private String name;

    private List<Integer> movies = new ArrayList<>();
}
