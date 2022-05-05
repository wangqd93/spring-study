package com.bycsmys.ioc.spring.applicationcontext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.*;

/**
 * {@link org.springframework.context.ApplicationContext} 当作ResourceLoader 使用
 */
public class ApplicationContextResourceTest {

    public static void main(String[] args) {

        ResourceLoader resourceLoader = new ClassPathXmlApplicationContext("anno-config.xml");
        Resource resource = resourceLoader.getResource("~/java_error_in_idea_11458.log");
        System.out.println("resource instanceof ClassPathResource = " + (resource instanceof ClassPathResource));
        System.out.println("resource.exists() = " + resource.exists());

        Resource resource1 = resourceLoader.getResource("http://www.spring21.cn");
        System.out.println("resource1 instanceof UrlResource = " + (resource1 instanceof UrlResource));

    }
}
