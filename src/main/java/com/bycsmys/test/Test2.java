package com.bycsmys.test;

public class Test2 {


    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new InterruptExample.MyThread1();
        thread1.start();
        thread1.interrupt();
        System.out.println("Main run");
    }


    public static class InterruptExample {
        public static class MyThread1 extends Thread {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread run");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
