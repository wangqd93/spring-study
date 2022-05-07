package com.bycsmys.aop.dynamicproxy;

public class SubjectImpl implements ISubject {

    @Override
    public void request(String name) {
        System.out.println("hello " + name);
    }
}
