package com.bycsmys.aop.aspectj.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MockAdvice {

    @Pointcut("execution(* destory(..))")
    public void destory() {
    }

    @Before("execution(public void  *.methodName(String))")
    public void setUpResourceFolder() {
    }

    @After("destory()")
    public void cleanUpResourcesIfNecessary() {
    }

}
