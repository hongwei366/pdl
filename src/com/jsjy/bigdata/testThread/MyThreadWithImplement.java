package com.jsjy.bigdata.testThread;

import java.util.Random;

/**
 * 继承方式实现多线程
 */
public class MyThreadWithImplement implements Runnable {
    int x;

    public MyThreadWithImplement(int x) {
        this.x = x;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("线程" + name + "线程run方法被调用");
        for (int i = 0; i < 20; i++) {
            System.out.println("线程" + name + "--" + x);
            try {
                Thread.sleep(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyThreadWithImplement(1),"thread-1");
        Thread thread2 = new Thread(new MyThreadWithImplement(2),"thread-2");
        thread1.start();
        //thread1.start();
        thread2.start();
    }


}
