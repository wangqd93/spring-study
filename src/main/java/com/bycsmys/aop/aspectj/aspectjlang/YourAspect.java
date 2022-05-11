package com.bycsmys.aop.aspectj.aspectjlang;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class YourAspect {

    @Pointcut("aspectj style point expression")
    public void pointcutMethod() {
    }

    @Pointcut("aspectj style point expression")
    public void pointcutMethod2() {

    }
}
