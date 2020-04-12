package ru.kinoservice.movie.parser.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface ConnectionService {

    Document getDocument(String url) throws IOException;
}
