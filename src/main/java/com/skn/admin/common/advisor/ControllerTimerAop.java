package com.skn.admin.common.advisor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ControllerTimerAop {

    @Pointcut("@annotation(com.skn.admin.config.annotation.TimeCheck)")
    public void TimeCheckMethods() {}

    @Around("TimeCheckMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 컨트롤러 메서드 실행 전 로그
        log.info("======================================================================================");
        log.info("✅ Starting execution of: " + joinPoint.getSignature().toShortString());
        log.info("======================================================================================");

        // 실제 메서드 실행
        Object proceed = joinPoint.proceed();


        // 컨트롤러 메서드 실행 후 로그
        log.info("======================================================================================");
        log.info("✅ Finished execution of: " + joinPoint.getSignature().toShortString());
        log.info("⏰ Duration: " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        log.info("======================================================================================");

        return proceed;
    }

}
