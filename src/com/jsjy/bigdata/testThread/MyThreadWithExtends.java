package com.jsjy.bigdata.testThread;

import java.util.Random;

/**
 * 继承方式实现多线程
 */
public class MyThreadWithExtends extends Thread {
    String flag;
    public MyThreadWithExtends(String flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        String tname = Thread.currentThread().getName();
        System.out.println(tname + "线程run方法被调用");
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            try{
                Thread.sleep(random.nextInt(10)*100);
                System.out.println(tname + "..." + flag);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new MyThreadWithExtends("a");
        Thread thread2 = new MyThreadWithExtends("b");
        thread1.start();
        //thread1.start();
        thread2.start();
    }


}
