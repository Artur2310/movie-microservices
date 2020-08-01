package ru.kinoservice.general.parser.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("ru.kinoservice.parser")
public class ParserProperties {

    private String url = "94.242.60.7";
    private String hostProxy = "127.0.0.1";
    private String portProxy = "8118";
}
