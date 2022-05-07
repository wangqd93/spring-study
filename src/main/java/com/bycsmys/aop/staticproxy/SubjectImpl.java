package com.bycsmys.aop.staticproxy;

public class SubjectImpl implements ISubject {

    @Override
    public void request() {
        System.out.println("hello world");
    }
}
