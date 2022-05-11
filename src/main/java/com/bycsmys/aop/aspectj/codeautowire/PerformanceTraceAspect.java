package com.bycsmys.aop.aspectj.codeautowire;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

@Aspect
public class PerformanceTraceAspect {

    private final Log logger = LogFactory.getLog(this.getClass());


    @Pointcut("execution(public void *.method1()) || execution(public void *.method2())")
    public void pointcutName() {
    }

    @Around("pointcutName()")
    public Object performanceTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            logger.info("PT in method[" + joinPoint.getSignature().getName() + "] >>>>" + stopWatch.toString());
        }
    }
}
