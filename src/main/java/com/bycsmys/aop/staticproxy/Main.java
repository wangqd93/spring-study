package com.bycsmys.aop.staticproxy;

public class Main {

    public static void main(String[] args) {
        SubjectProxy subjectProxy = new SubjectProxy(new SubjectImpl());
        subjectProxy.request();
    }
}
