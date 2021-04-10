package com.epro.lvall;

/**
 * @author zzy
 * @date 2019/8/22
 * volatile....
 */

public class VariableTest {

    public static volatile boolean flag = false;
//    public static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();

        new Thread(threadA, "threadA").start();
        Thread.sleep(1000l);//为了保证threadA比threadB先启动，sleep一下
        new Thread(threadB, "threadB").start();


    }

    static class ThreadA extends Thread {
        public void run() {
            while (true) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " : flag is 2 " + flag);
                    break;
                } else {
//                    System.out.println(Thread.currentThread().getName() + " : flag is 1 " + flag);
                }
            }

        }

    }

    static class ThreadB extends Thread {
        public void run() {
            flag = true;
            System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
        }
    }
}