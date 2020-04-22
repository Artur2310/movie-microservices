package ru.kinoservice.person.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonDataApplication.class, args);
	}

}
