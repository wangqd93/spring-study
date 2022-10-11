package com.bycsmys.test;

import java.util.concurrent.*;

public class Test1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get(1, TimeUnit.MILLISECONDS));
    }


    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(2000);
            return 123;
        }

    }
}
