package ru.otus.spring.petrova.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  final static Logger logger = LogManager.getLogger(LoggingAspect.class);
  @Before("within(ru.otus.spring.petrova..*)")
  public void logBefore(JoinPoint joinPoint) {
    logger.debug("start method : " + joinPoint.getSignature().getName());
  }

  @After("within(ru.otus.spring.petrova..*)")
  public void logAfter(JoinPoint joinPoint) {
    logger.debug("end method : " + joinPoint.getSignature().getName());
  }
}
