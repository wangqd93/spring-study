package com.bycsmys.aop.aspectj.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.Resource;

@Aspect
public class ResourceSetUpAspect {

    private Resource resource;

    @Pointcut("execution(boolean *.excute())")
    public void aspect() {
    }

    @Before("aspect()")
    public void setupResourcesBefore() throws Throwable {
        if (getResource().exists()) {
            System.out.println("resource exists");
        }
    }

    public Resource getResource() {
        return resource;
    }
}
