package com.example.multi_thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafe2 {

    public AtomicInteger countNum = new AtomicInteger(0);
    // public static AtomicInteger countNum = new AtomicInteger(0);

    public static void main(String[] args) {

        ThreadSafe2 main = new ThreadSafe2();

        Runnable runner = new Runnable() {

            public void run() {
                for (int i = 0; i < 1000; i++) {
                    main.countNum.getAndIncrement();
                    try{
                        Thread.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        long startTime = System.currentTimeMillis();

        Thread th1 = new Thread(runner);
        Thread th2 = new Thread(runner);
        Thread th3 = new Thread(runner);

        th1.start();
        th2.start();
        th3.start();

        try {
            th1.join();
            th2.join();
            th3.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        System.out.println(main.countNum);
        System.out.println(endTime - startTime);

    }
}