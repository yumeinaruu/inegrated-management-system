package com.yumeinaruu.iis.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

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

    @Around(value = "@annotation(com.yumeinaruu.iis.aop.TimeAop)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalTime startTime = LocalTime.now();
        Object o = joinPoint.proceed();
        LocalTime endTime = LocalTime.now();
        log.info("Method execution time(in nanos): " + (endTime.getNano() - startTime.getNano()));
        return o;
    }
}
