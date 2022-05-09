package com.bycsmys.aop.proxyfactory;

public class MockTask implements ITask {
    @Override
    public void execute(TaskExcutionContext ctx) {
        System.out.println("task executed");
    }
}
