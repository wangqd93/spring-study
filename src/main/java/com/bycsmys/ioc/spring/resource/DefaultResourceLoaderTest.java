package com.bycsmys.ioc.spring.resource;

import org.springframework.core.io.*;

import java.io.IOException;

public class DefaultResourceLoaderTest {

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();

        Resource resource = resourceLoader.getResource("~/java_error_in_idea_11458.log");
        System.out.println("resource instanceof ClassPathResource = " + (resource instanceof ClassPathResource));
        System.out.println(resource.exists());

        Resource resource1 = resourceLoader.getResource("file:~/java_error_in_idea_11458.log");
        System.out.println("resource1 instanceof UrlResource = " + (resource1 instanceof UrlResource));

        Resource resource2 = resourceLoader.getResource("http://www.spring21.cn");
        System.out.println("resource2 instanceof UrlResource = " + (resource2 instanceof UrlResource));

        try {
            resource.getFile();

        } catch (IOException e) {
            System.out.println("resource.getFile() e:" + e.getStackTrace());
        }

        try {
            resource1.getFile();
        } catch (IOException e) {
            System.out.println("resource1.getFile() e:" + e.getStackTrace());

        }

    }
}
