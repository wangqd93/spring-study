package com.bycsmys.aop.proxyfactory.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class TesterFeatureIntroductionInterceptor extends DelegatingIntroductionInterceptor implements ITester {

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return super.invoke(mi);
    }

    @Override
    public void testSoftware() {
        System.out.println("i will ensure the quality");

    }
}
