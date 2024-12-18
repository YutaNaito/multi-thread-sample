package com.example.multi_thread;

import java.util.concurrent.CountDownLatch;
import java.util.Random;

public class Reentrancy {

    private static final Object lock = new Object();

    public static void main(String[] args) {

        Reentrancy main = new Reentrancy();

        Runnable runner = new Runnable() {
            public void run() {
                // 関数を呼び出す中で同じロックを使用する場合に
                // 再入可能でないとデッドロックが発生する
                main.methodB();

                // 再帰呼び出しでも同じロックを使用する場合も
                // 同様に再入可能でないとデッドロックが発生する
                main.recursiveFunc(5);
            }
        };

        Thread th1 = new Thread(runner);
        Thread th2 = new Thread(runner);

        th1.start();
        th2.start();

        try {
            th1.join();
            th2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void methodA() {
        synchronized (lock) {
            System.out.println("methodA");
        }
    }

    public void methodB() {
        synchronized (lock) {
            methodA();
            System.out.println("methodB");
        }
    }

    // 再入可能という言葉は、マルチスレッドの脈絡ではなく
    // 割り込みハンドラの考えが元になっており、複数のコンテキストから
    // 正しくデータが更新されないという意味で使われることもある
    public void recursiveFunc(int count) {
        synchronized (lock) {
            System.out.println("recursive");

            if (count > 0) {
                recursiveFunc(count - 1);
            }
        }
    }
}
