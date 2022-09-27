package com.bycsmys.ioc.applicaioncontext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.stream.Stream;

public class ApplicationContextMethodTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("anno-config.xml");


        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);

        System.out.println("---------------");

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Async.class);
        System.out.println("beansWithAnnotation = " + beansWithAnnotation);


        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(Async.class);
        Stream.of(beanNamesForAnnotation).forEach(System.out::println);


    }
}
