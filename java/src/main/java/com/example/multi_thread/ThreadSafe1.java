package com.example.multi_thread;

public class ThreadSafe1 {

    public int countNum = 0;

    public static void main(String[] args) {

        ThreadSafe1 main = new ThreadSafe1();

        Runnable runner = new Runnable() {

            // 挙動としては順次実行と変わらない
            public synchronized void run() {
                for (int i = 0; i < 1000; i++) {
                    main.countNum++;
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
        System.out.println(endTime-startTime);

    }
}