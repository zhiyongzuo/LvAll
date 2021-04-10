package com.epro.lvall;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zzy
 * @date 2020/9/15
 */
public class CountDownTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch cdl = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            CountRunnable runnable = new CountRunnable(cdl);
            pool.execute(runnable);
        }
    }
}

    class CountRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        public CountRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            try {
                //synchronized (this)为什么和synchronized (countDownLatch)不一样呢
                //synchronized (CountRunnable.class)  按顺序
                //synchronized (countDownLatch)       按顺序
                //synchronized (this) {             不按顺序
                synchronized (this) {
                    /*** 每次减少一个容量*/
                    countDownLatch.countDown();
                    System.out.println("thread counts = " + (countDownLatch.getCount()));
                }
                countDownLatch.await();
                System.out.println("concurrency counts = " + (100 - countDownLatch.getCount()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
