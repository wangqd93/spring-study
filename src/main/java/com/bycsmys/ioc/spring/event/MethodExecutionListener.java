package com.bycsmys.ioc.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MethodExecutionListener implements ApplicationListener <MethodExecutionEvent>{

    @Override
    public void onApplicationEvent(MethodExecutionEvent event) {
        System.out.println("event.getMethodExecutionStatus() = " + event.getMethodExecutionStatus());
        System.out.println("event.getMethodName() = " + event.getMethodName());

    }
}
