package ru.kinoservice.general.parser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class AppConfig {

    @Bean
    public ReentrantLock getParseLock() {
        return new ReentrantLock();
    }
}
