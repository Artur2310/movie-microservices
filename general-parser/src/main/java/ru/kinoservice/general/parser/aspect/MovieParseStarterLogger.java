package ru.kinoservice.general.parser.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MovieParseStarterLogger {

    private static Logger logger = LoggerFactory.getLogger(MovieParseStarterLogger.class);
    private long profileTime = 0;

    @Pointcut("execution(public * ru.kinoservice.general.parser.service.MovieParseStarter.start(..))")
    public void callParseMovieMethod() {
    }

    @Before("callParseMovieMethod()")
    public void beforeCallAtMethod1(JoinPoint jp) {
        profileTime = System.currentTimeMillis();
        logger.info("Start parse movies!");
    }

    @AfterReturning("callParseMovieMethod()")
    public void afterReturningCallAt(JoinPoint jp) {
        profileTime = (System.currentTimeMillis() - profileTime) / 1000;
        logger.info("End parse movies! Time = " + profileTime + " (second)");
    }

    @AfterThrowing("callParseMovieMethod()")
    public void afterThrowingCallAt(JoinPoint jp) {
        logger.error("Error parse movies!");
    }
}
