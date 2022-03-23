package com.example.demo.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MainControllerAspect {
    private final Logger logger = Logger.getLogger(MainControllerAspect.class);

    @Pointcut("execution(public * com.example.demo.controller.HomeController.get1())")
    private void get1Method() {
    }

    @Pointcut("within(com.example.demo.controller.HomeController)")
    private void homeController() {}

    @Around("get1Method()")
    public Object get1Around(ProceedingJoinPoint pjp) throws Throwable {
        logger.warn("[1] Around get1 method:");
        Object retVal = pjp.proceed();
        logger.warn("[2] Around get1 method:");

        return retVal;
    }

    @Before("get1Method()")
    public void beforeGet1Method(JoinPoint joinPoint) {
        logger.warn("Before get1 method:" + joinPoint.getSignature());
    }

    @Before("homeController()")
    public void beforeHomeController(JoinPoint joinPoint) {
        logger.warn("Before method:" + joinPoint.getSignature());
    }

}
