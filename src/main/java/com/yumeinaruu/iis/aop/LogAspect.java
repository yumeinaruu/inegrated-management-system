package com.yumeinaruu.iis.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * com.yumeinaruu.iis.service.*.*())")
    public static void logPointcut() {}

    @Before(value = "logPointcut()")
    public void logAtTheBeginning(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + " : method started");
    }

    @After(value = "logPointcut()")
    public void createLogAtTheEnd(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + " ended.");
    }
}
