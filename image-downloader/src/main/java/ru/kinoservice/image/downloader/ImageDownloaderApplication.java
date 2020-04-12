package ru.kinoservice.image.downloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class ImageDownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageDownloaderApplication.class, args);
	}

}
