package ru.kinoservice.general.parser.exception;

public class PageAccessException extends RuntimeException {

    public PageAccessException(Throwable e) {
        super(e);
    }

    public PageAccessException() {
    }
}
