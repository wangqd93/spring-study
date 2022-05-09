package com.bycsmys.aop.proxyfactory.introduction;

import org.springframework.aop.framework.ProxyFactory;

public class IntroductionProxyFactoryTest {

    public static void main(String[] args) {

        ProxyFactory proxyFactory = new ProxyFactory(new Developer());
        proxyFactory.setInterfaces(new Class[]{IDeveloper.class,ITester.class});

        TesterFeatureIntroductionInterceptor testerFeatureIntroductionInterceptor = new TesterFeatureIntroductionInterceptor();
        proxyFactory.addAdvice(testerFeatureIntroductionInterceptor);

        Object proxy = proxyFactory.getProxy();
        ((ITester) proxy).testSoftware();
        ((IDeveloper) proxy).developSoftware();

    }
}
