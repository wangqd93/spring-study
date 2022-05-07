package com.bycsmys.aop.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SubjectHandler implements InvocationHandler {
    private Object object;

    public SubjectHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("before " + method.getName() + " args :" + Arrays.toString(objects));
        System.out.println("o.class:" + o.getClass());
        Object invoke = method.invoke(object, objects);
        System.out.println("after " + method.getName());

        return invoke;
    }
}
