package com.bycsmys.aop.dynamicproxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        ISubject o = (ISubject)Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{ISubject.class}, new SubjectHandler(new SubjectImpl()));
        o.request("wangqd");

    }
}
