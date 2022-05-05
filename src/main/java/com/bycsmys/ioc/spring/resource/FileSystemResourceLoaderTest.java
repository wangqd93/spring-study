package com.bycsmys.ioc.spring.resource;

import org.springframework.core.io.*;

public class FileSystemResourceLoaderTest {
    public static void main(String[] args) {

        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        Resource resource = resourceLoader.getResource("~/java_error_in_idea_11458.log");
        System.out.println("resource instanceof FileSystemResource = " + (resource instanceof FileSystemResource));
        System.out.println("resource.exists() = " + resource.exists());

        Resource resource1 = resourceLoader.getResource("file:~/java_error_in_idea_11458.log");
        System.out.println("resource1 instanceof UrlResource = " + (resource1 instanceof UrlResource));
    }
}
