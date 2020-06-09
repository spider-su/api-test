package com.example;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Pointcut("execution(* com.company.division.project.web.rest..*.*(..))")
    public void anyControllersMethod() {
    }

    @Before("anyControllersMethod()")
    public void loggingBefore(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0) {
            log.info(String.format("\n%s start processing", method));
            return;
        }

        int i = 1;
        log.info(String.format("%s start processing with parameters:", method));
        for (Object param : args) {
            log.info(String.format("\t%d parameter is '%s'", i++, param));
        }
    }

    @AfterReturning(pointcut = "anyControllersMethod()", returning = "retVal")
    public void logAfterMethod(JoinPoint joinPoint, Object retVal) {
        if (retVal == null) {
            log.info(String.format("%s completed successfully. ", (joinPoint.getSignature().toShortString())));
        } else {
            log.info(String.format("%s completed successfully with result: ", (joinPoint.getSignature().toShortString())));
            log.info(String.valueOf(retVal));
        }
    }

    @AfterThrowing(pointcut = "anyControllersMethod()", throwing = "e")
    public void logAfterThrowingAllMethods(JoinPoint joinPoint, Exception e) {
        log.error(String.format("%s failed with exception: ", joinPoint.getSignature().toShortString()));
        log.error(e.getMessage());
    }
}
