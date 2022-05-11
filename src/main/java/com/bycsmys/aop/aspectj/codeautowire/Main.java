package com.bycsmys.aop.aspectj.codeautowire;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class Main {

    public static void main(String[] args) {
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory();

        aspectJProxyFactory.setTarget(new Foo());
        aspectJProxyFactory.addAspect(PerformanceTraceAspect.class);

        Foo proxy = (Foo) aspectJProxyFactory.getProxy();
        proxy.method1();
        proxy.method2();
    }
}
