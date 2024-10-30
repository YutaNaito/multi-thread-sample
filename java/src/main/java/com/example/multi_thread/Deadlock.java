package com.example.multi_thread;

import java.util.concurrent.CountDownLatch;
import java.util.Random;

/* デッドロックを起こすサンプル
 * デッドロックの条件は以下の全てを満たさない場合に起こる
 * 1. 利用すべきロックが2つ以上存在しない
 * 2. 複数のスレッドで複数のロックを共有しない
 * 3. ロックをする順番が決まっている
 */
public class Deadlock {

    // 利用するロックが2つ以上ある
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    private static CountDownLatch countDownLatch = new CountDownLatch(4000);

    public int countNum1 = 0;
    public int countNum2 = 0;

    Random rand = new Random();

    public static void main(String[] args) {

        Deadlock main = new Deadlock();

        Runnable runner1 = new Runnable() {

            public void run() {
                for (int i = 0; i < 2000; i++) {

                    synchronized (lock1) {
                        main.countNum1++;

                        try {
                            int tmp = main.rand.nextInt(5) + 1;
                            Thread.sleep(tmp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        synchronized (lock2) {
                            main.countNum2++;
                        }
                    }
                    countDownLatch.countDown();
                }
            }
        };

        Runnable runner2 = new Runnable() {

            public void run() {
                for (int i = 0; i < 2000; i++) {

                    synchronized (lock2) {
                        main.countNum1++;

                        try {
                            int tmp = main.rand.nextInt(5) + 1;
                            Thread.sleep(tmp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        synchronized (lock1) {
                            main.countNum2++;
                        }
                    }
                    countDownLatch.countDown();
                }
            }
        };

        long startTime = System.currentTimeMillis();

        /*
         * th1とth2の複数のスレッドでlock1とlock2の複数のロックを共有している.
         * th1のスレッドでは、lock2,lock1の順番でロックを取得しており、th2のスレッドでは、逆の順番でロックを取得し、
         * ロックの順番が決まっていない.
         */
        Thread th1 = new Thread(runner1);
        Thread th2 = new Thread(runner2);

        th1.start();
        th2.start();

        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        System.out.println(main.countNum1);
        System.out.println(main.countNum2);
        System.out.println((endTime - startTime) / 1000);

    }
}