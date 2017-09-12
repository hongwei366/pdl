package com.jsjy.bigdata.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁操作
 */
public class MyReentrantReadWriteLock {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final MyReentrantReadWriteLock test =new MyReentrantReadWriteLock();

        for (int i = 0; i < 3; i++) {
            new Thread(){
                @Override
                public void run() {
                    test.get(Thread.currentThread());
                    test.write(Thread.currentThread());
                }
            }.start();
        }
    }

    /**
     * 读操作
     * @param thread
     */
    private void get(Thread thread) {
        rwl.readLock().lock();
        try{
            long startTime =System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime <= 1){
                System.out.println(thread.getName()+"正在进行读操作");
            }
            System.out.println(thread.getName() + "读操作完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放读锁
            rwl.readLock().unlock();
        }
    }

    /**
     * 写操作
     * @param thread
     */
    private void write(Thread thread) {
        //上写锁
        rwl.writeLock().lock();
        try{
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() -start<=1){
                System.out.println(thread.getName()+"正在进行写操作");
            }
            System.out.println(thread.getName() + "写操作完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwl.writeLock().unlock();//释放写锁
        }
    }


}
