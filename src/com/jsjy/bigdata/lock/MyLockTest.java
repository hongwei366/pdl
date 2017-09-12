package com.jsjy.bigdata.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockTest {
    private static List<Integer> list = new ArrayList<Integer>();
    static Lock lock = new ReentrantLock();

    public static <E> void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                //上锁
                lock.lock();
                try {
                    System.out.println(thread.getName() + "得到了锁");
                    for (int i = 0; i < 5; i++) {
                        list.add(i);
                    }
                } catch (Exception e) {
                    System.out.println("------exception-----");
                } finally {
                    System.out.println(thread.getName() + "释放了锁");
                    lock.unlock();
                }
            }
        }.start();

        new Thread() {
            public void run() {
                Thread thread = Thread.currentThread();
                lock.lock();
                try {
                    System.out.println(thread.getName() + "得到了锁");
                    for (int i = 0; i < 5; i++) {
                        list.add(i);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    System.out.println(thread.getName() + "释放了锁");
                    lock.unlock();
                }
            }
        }.start();
    }

}
