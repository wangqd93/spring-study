package com.bycsmys.aop.proxyfactory;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class ProxyFactoryTest {

    public static void main(String[] args) {

        // 基于接口代理
        interfaceProxyFactory();

        // 基于类代理
//        noInterfaceProxyFactory();
    }

    /**
     * 无接口
     */
    private static void noInterfaceProxyFactory() {
        ProxyFactory proxyFactory = new ProxyFactory(new Executable());
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setMappedName("execute");
        nameMatchMethodPointcutAdvisor.setAdvice(new PerformanceMethodInterceptor());

        proxyFactory.addAdvisor(nameMatchMethodPointcutAdvisor);

        Executable proxy = (Executable) proxyFactory.getProxy();
        System.out.println("proxy.getClass() = " + proxy.getClass());
        proxy.execute();
    }

    /**
     * 有接口
     */
    private static void interfaceProxyFactory() {
        MockTask mockTask = new MockTask();
        ProxyFactory proxyFactory = new ProxyFactory(mockTask);

        // 强制指定cglib
//        proxyFactory.setProxyTargetClass(true);

        proxyFactory.setInterfaces(new Class[]{ITask.class});

        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setMappedName("execute");
        nameMatchMethodPointcutAdvisor.setAdvice(new PerformanceMethodInterceptor());

        proxyFactory.addAdvisor(nameMatchMethodPointcutAdvisor);

        ITask iTask = (ITask) proxyFactory.getProxy();
        System.out.println("iTask.getClass() = " + iTask.getClass());
        iTask.execute(null);
    }
}
