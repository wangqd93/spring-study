package com.bycsmys.ioc.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("anno-config.xml");
        MethodExecutionPublisher bean = applicationContext.getBean(MethodExecutionPublisher.class);
        bean.methodToMonitor();

    }
}
