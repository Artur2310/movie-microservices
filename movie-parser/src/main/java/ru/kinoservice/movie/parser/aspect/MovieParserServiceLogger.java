package ru.kinoservice.movie.parser.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kinoservice.movie.parser.service.MovieParserService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class MovieParserServiceLogger {

    private static Logger logger = LoggerFactory.getLogger(MovieParserService.class);

    @Pointcut("execution(public * ru.kinoservice.movie.parser.service.MovieParserService.parse(..))")
    public void callParseMethod() {
    }

    @Before("callParseMethod()")
    public void beforeCallAtMethod1(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("Start parse movie :" + args);
    }

    @After("callParseMethod()")
    public void afterCallAt(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("End parse movie :" + args);
    }
}
