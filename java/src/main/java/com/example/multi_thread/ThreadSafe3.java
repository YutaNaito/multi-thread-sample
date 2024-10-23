package com.example.multi_thread;

public class ThreadSafe3 {


    public int countNum = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {

        ThreadSafe3 main = new ThreadSafe3();

        Runnable runner = new Runnable() {

            public void run() {
                for (int i = 0; i < 1000; i++) {
                    synchronized (lock) {
                        main.countNum++;
                    }
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