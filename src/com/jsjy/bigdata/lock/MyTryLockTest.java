package com.jsjy.bigdata.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyTryLockTest {
    private static List<Integer> list = new ArrayList<Integer>();
    static Lock lock = new ReentrantLock();//共用一把锁
    public static  void main(String[] args) {

        new Thread(){
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                boolean tryLock = lock.tryLock();
                System.out.println(thread.getName()+"--"+ tryLock);
                if(tryLock){
                    try {
                        System.out.println(thread.getName() + "得到了锁");
                        for (int i = 0; i < 5; i++) {
                            list.add(i);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        System.out.println(thread.getName() + "释放了锁");
                        lock.unlock();
                    }
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                boolean tryLock = lock.tryLock();
                System.out.println(thread.getName()+"--"+ tryLock);
                if(tryLock){
                    try {
                        System.out.println(thread.getName() + "得到了锁");
                        for (int i = 0; i < 5; i++) {
                            list.add(i);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        System.out.println(thread.getName() + "释放了锁2");
                        lock.unlock();
                    }
                }
            }
        }.start();




    }

}
