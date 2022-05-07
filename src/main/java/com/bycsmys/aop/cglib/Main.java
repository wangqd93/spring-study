package com.bycsmys.aop.cglib;

import com.google.inject.internal.asm.$MethodTooLargeException;
import org.springframework.cglib.proxy.Enhancer;

public class Main {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OriginObject.class);
        enhancer.setCallback(new MethodCallback());

        OriginObject o = (OriginObject)enhancer.create();
        o.test("wangqd");

    }
}
