package com.epro.lvall;

import com.blankj.utilcode.util.ThreadUtils;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.blankj.utilcode.util.ThreadUtils.cancel;

/**
 * @author zzy
 * @date 2020/9/15
 */

public class ThreadUtilsTest {

    @Test
    public void executeByFixed() throws Exception {
        asyncTest(10, new TestRunnable<String>() {
            @Override
            public void run(final int index, CountDownLatch latch) {
                final TestTask<String> task = new TestTask<String>(latch) {
                    @Override
                    public String doInBackground() throws Throwable {
                        System.out.println("doInBackground--Thread.currentThread().getName():" + Thread.currentThread().getName());
                        Thread.sleep(500 + index * 10);
                        if (index < 4) {
                            return Thread.currentThread() + " :" + index;
                        } else if (index < 7) {
                            cancel();
                            return null;
                        } else {
                            throw new NullPointerException(String.valueOf(index));
                        }
                    }

                    @Override
                    void onTestSuccess(String result) {
                        System.out.println(result);
                    }
                };
                ThreadUtils.executeByFixed(3, task);
            }
        });
    }

    @Test
    public void executeByFixedWithDelay() throws Exception {
        asyncTest(10, new TestRunnable<String>() {
            @Override
            public void run(final int index, CountDownLatch latch) {
                final TestTask<String> task = new TestTask<String>(latch) {
                    @Override
                    public String doInBackground() throws Throwable {
                        Thread.sleep(500);
                        if (index < 4) {
                            return Thread.currentThread() + " :" + index;
                        } else if (index < 7) {
                            cancel();
                            return null;
                        } else {
                            throw new NullPointerException(String.valueOf(index));
                        }
                    }

                    @Override
                    void onTestSuccess(String result) {
                        System.out.println(result);
                    }
                };
                ThreadUtils.executeByFixedWithDelay(3, task, 500 + index * 10, TimeUnit.MILLISECONDS);
            }
        });
    }

    @Test
    public void executeByFixedAtFixRate() throws Exception {
        asyncTest(10, new TestRunnable<String>() {
            @Override
            public void run(final int index, CountDownLatch latch) {
                final TestScheduledTask<String> task = new TestScheduledTask<String>(latch, 3) {
                    @Override
                    public String doInBackground() throws Throwable {
                        Thread.sleep(500 + index * 10);
                        if (index < 4) {
                            return Thread.currentThread() + " :" + index;
                        } else if (index < 7) {
                            cancel();
                            return null;
                        } else {
                            throw new NullPointerException(String.valueOf(index));
                        }
                    }

                    @Override
                    void onTestSuccess(String result) {
                        System.out.println(result);
                    }
                };
                ThreadUtils.executeByFixedAtFixRate(3, task, 3000 + index * 10, TimeUnit.MILLISECONDS);
            }
        });
    }

    abstract static class TestScheduledTask<T> extends ThreadUtils.Task<T> {

        private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
        private int mTimes;
        CountDownLatch mLatch;

        TestScheduledTask(final CountDownLatch latch, final int times) {
            mLatch = latch;
            mTimes = times;
        }

        abstract void onTestSuccess(T result);

        @Override
        public void onSuccess(T result) {
            onTestSuccess(result);
            if (ATOMIC_INTEGER.addAndGet(1) % mTimes == 0) {
                mLatch.countDown();
            }
        }

        @Override
        public void onCancel() {
            System.out.println(Thread.currentThread() + " onCancel: ");
            mLatch.countDown();
        }

        @Override
        public void onFail(Throwable t) {
            System.out.println(Thread.currentThread() + " onFail: " + t);
            mLatch.countDown();
        }
    }

    abstract static class TestTask<T> extends ThreadUtils.Task<T> {
        CountDownLatch mLatch;

        TestTask(final CountDownLatch latch) {
            mLatch = latch;
        }

        abstract void onTestSuccess(T result);

        @Override
        public void onSuccess(T result) {
            onTestSuccess(result);
            mLatch.countDown();
        }

        @Override
        public void onCancel() {
            System.out.println(Thread.currentThread() + " onCancel: ");
            mLatch.countDown();
        }

        @Override
        public void onFail(Throwable t) {
            System.out.println(Thread.currentThread() + " onFail: " + t);
            mLatch.countDown();
        }
    }

    <T> void asyncTest(int threadCount, TestRunnable<T> runnable) throws Exception {
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            runnable.run(i, latch);
        }
        latch.await();
        System.out.println("全部执行完成---zzy");
    }

    interface TestRunnable<T> {
        void run(final int index, CountDownLatch latch);
    }
}