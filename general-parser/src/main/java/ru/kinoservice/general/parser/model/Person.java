package ru.kinoservice.general.parser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private Integer id;

    private int sourceId;

    private String url;

    private String pictureUrl;

    private String name;

}
