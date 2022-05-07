package com.bycsmys.aop.staticproxy;

public class SubjectProxy implements ISubject {
    private ISubject subject;

    public SubjectProxy(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public void request() {
        System.out.println("before request");
        subject.request();
        System.out.println("after request");

    }
}
