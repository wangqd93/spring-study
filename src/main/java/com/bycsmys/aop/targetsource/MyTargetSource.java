package com.bycsmys.aop.targetsource;

import com.bycsmys.aop.proxyfactory.ITask;
import org.springframework.aop.TargetSource;

public class MyTargetSource implements TargetSource {
    private ITask taskOne;
    private ITask taskTwo;

    private int counter;

    public MyTargetSource(ITask taskOne, ITask taskTwo) {
        this.taskOne = taskOne;
        this.taskTwo = taskTwo;
    }

    @Override
    public Class<?> getTargetClass() {
        return ITask.class;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public Object getTarget() throws Exception {
        try{
            if(counter % 2 == 0){
                return taskTwo;
            }
            return taskOne;
        }finally {
            counter ++;
        }
    }

    @Override
    public void releaseTarget(Object target) throws Exception {

    }
}
