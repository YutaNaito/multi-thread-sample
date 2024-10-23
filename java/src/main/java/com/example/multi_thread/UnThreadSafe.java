package com.example.multi_thread;

public class UnThreadSafe {

    public int countNum = 0;

    public static void main(String[] args) {

        UnThreadSafe main = new UnThreadSafe();

        Runnable runner = new Runnable() {

            public void run() {
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

        System.out.println(main.countNum);

    }
}