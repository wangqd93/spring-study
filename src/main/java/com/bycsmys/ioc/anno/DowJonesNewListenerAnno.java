package com.bycsmys.ioc.anno;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author wangqd
 * @DATE 2019-03-02
 */
@Component
public class DowJonesNewListenerAnno implements IFXNewsListenerAnno {

    public DowJonesNewListenerAnno() {
        System.out.println("DowJonesNewListenerAnno construct invoked");

        Arrays.stream(Thread.currentThread().getStackTrace())
                .forEach(t -> {
                    System.out.println("className:" + t.getClassName() + " methodName:" + t.getMethodName() + " lineNumber:" + t.getLineNumber());
                });

    }
}
