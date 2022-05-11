package com.bycsmys.aop.targetsource;

import com.bycsmys.aop.proxyfactory.ITask;
import com.bycsmys.aop.proxyfactory.TaskExcutionContext;
import org.springframework.aop.framework.ProxyFactory;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        ITask taskOne = (ctx) -> {
            System.out.println("taskOne");
        };

        ITask taskTwo = (ctx) -> {
            System.out.println("taskTwo");
        };

        MyTargetSource myTargetSource = new MyTargetSource(taskOne, taskTwo);

        ProxyFactory factory = new ProxyFactory();
        factory.setTargetSource(myTargetSource);

        ITask iTask = (ITask) factory.getProxy();
        System.out.println("iTask.getClass() = " + iTask.getClass());

        iTask.execute(new TaskExcutionContext());
        iTask.execute(new TaskExcutionContext());

    }
}
