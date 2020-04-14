package ru.kinoservice.movie.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieParserApplication.class, args);
	}

}
