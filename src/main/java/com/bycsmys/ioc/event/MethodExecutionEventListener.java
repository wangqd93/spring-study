package com.bycsmys.ioc.event;


import java.util.EventListener;

public interface MethodExecutionEventListener extends EventListener {

    void onMethodBegin(MethodExecutionEvent executionEvent);

    void onMethodEnd(MethodExecutionEvent executionEvent);
}
