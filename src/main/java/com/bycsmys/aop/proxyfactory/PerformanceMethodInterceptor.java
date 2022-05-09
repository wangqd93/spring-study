package com.bycsmys.aop.proxyfactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;


public class PerformanceMethodInterceptor implements MethodInterceptor {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();
            return methodInvocation.proceed();
        } catch (Exception e) {
            stopWatch.stop();
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(stopWatch.toString());
            }
        }
        return null;
    }

}
