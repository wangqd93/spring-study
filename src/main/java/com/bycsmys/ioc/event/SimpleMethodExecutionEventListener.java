package com.bycsmys.ioc.event;

public class SimpleMethodExecutionEventListener implements MethodExecutionEventListener {

    @Override
    public void onMethodBegin(MethodExecutionEvent executionEvent) {
        System.out.println("onMethodBegin executionEvent.getMethodName() = " + executionEvent.getMethodName());
    }

    @Override
    public void onMethodEnd(MethodExecutionEvent executionEvent) {
        System.out.println("onMethodEnd executionEvent.getMethodName() = " + executionEvent.getMethodName());
    }
}
