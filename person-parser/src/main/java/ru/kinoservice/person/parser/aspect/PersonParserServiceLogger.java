package ru.kinoservice.person.parser.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class PersonParserServiceLogger {

    private static Logger logger = LoggerFactory.getLogger(PersonParserServiceLogger.class);

    @Pointcut("execution(public * ru.kinoservice.person.parser.service.PersonParserService.parse(..))")
    public void callParseMethod() {
    }

    @Before("callParseMethod()")
    public void beforeCallAtMethod1(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("Start parse person :" + args);
    }

    @AfterReturning("callParseMethod()")
    public void afterReturningCallAt(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("Success parse person :" + args);
    }

    @AfterThrowing("callParseMethod()")
    public void afterThrowingCallAt(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.error("Error parse person :" + args);
    }

}
