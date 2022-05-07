package com.bycsmys.aop.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MethodCallback implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before " + "before");
        System.out.println("o.getClass() = " + o.getClass());
        Object invoke = methodProxy.invokeSuper(o, args);
        System.out.println("after " + "after");
        return invoke;
    }
}
