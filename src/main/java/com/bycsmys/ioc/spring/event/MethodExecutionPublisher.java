package com.bycsmys.ioc.spring.event;

import com.bycsmys.ioc.event.MethodExecutionStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class MethodExecutionPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public void methodToMonitor() {
        MethodExecutionEvent methodExecutionEvent = new MethodExecutionEvent(this,"methodToMonitor", MethodExecutionStatus.START);
        this.applicationEventPublisher.publishEvent(methodExecutionEvent);

        MethodExecutionEvent executionEvent = new MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.END);
        this.applicationEventPublisher.publishEvent(executionEvent);

    }


}
