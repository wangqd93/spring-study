package com.bycsmys.test;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class Test3 {

    public static void main(String[] args) throws MalformedURLException {
        URL ipAndPort = new URL("http://10.190.46.103:8867/a");

        String s = constructFullUrl(ipAndPort, "ProductController/getProduct?ids=3170770222150533126");
        System.out.println(s);


    }

    public static String constructFullUrl(URL url, String path) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(url.getProtocol())
                .host(url.getHost())
                .port(url.getPort())
                .pathSegment(url.getPath(), path).build();
        return uriComponents.toUriString();
    }

}
