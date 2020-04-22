package ru.kinoservice.person.data.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonNotFoundException extends RuntimeException {

    Integer id;
}
