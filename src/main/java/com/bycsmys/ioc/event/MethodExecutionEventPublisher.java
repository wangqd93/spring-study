package com.bycsmys.ioc.event;

import java.util.ArrayList;
import java.util.List;

public class MethodExecutionEventPublisher {

    private List<MethodExecutionEventListener> listenerList = new ArrayList<>();


    public void methodToMonitor() {
        MethodExecutionEvent methodExecutionEvent = new MethodExecutionEvent(this, "methodToMonitor");
        publishEvent(MethodExecutionStatus.START, methodExecutionEvent);
    }

    protected void publishEvent(MethodExecutionStatus status, MethodExecutionEvent methodExecutionEvent) {
        List<MethodExecutionEventListener> methodExecutionEventListeners = new ArrayList<>(listenerList);
        for (MethodExecutionEventListener methodExecutionEventListener : methodExecutionEventListeners) {
            if (MethodExecutionStatus.START.equals(status)) {
                methodExecutionEventListener.onMethodBegin(methodExecutionEvent);
            } else {
                methodExecutionEventListener.onMethodEnd(methodExecutionEvent);
            }
        }
    }

    public void addMethodExecutionEventListener(MethodExecutionEventListener listener) {
        this.listenerList.add(listener);
    }

    public void removeListener(MethodExecutionEventListener methodExecutionEventListener) {
        if (this.listenerList.contains(methodExecutionEventListener)) {
            this.listenerList.remove(methodExecutionEventListener);
        }
    }

    public void removeAllListeners() {
        this.listenerList.clear();
    }

    public static void main(String[] args) {
        MethodExecutionEventPublisher methodExecutionEventPublisher = new MethodExecutionEventPublisher();
        methodExecutionEventPublisher.addMethodExecutionEventListener(new SimpleMethodExecutionEventListener());
        methodExecutionEventPublisher.methodToMonitor();
    }
}
