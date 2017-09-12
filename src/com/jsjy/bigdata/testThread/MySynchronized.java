package com.jsjy.bigdata.testThread;

/**
 * Synchronized关键字--并发测试
 */
public class MySynchronized {
    public static void main(String[] args) {
        final MySynchronized mySynchronized = new MySynchronized();
        final MySynchronized mySynchronized2 = new MySynchronized();
        new Thread("thread1") {
            public void run() {
                synchronized (mySynchronized){
                    try {
                        System.out.println(this.getName() + " start");
                        int i = 1 / 0;
                        Thread.sleep(5000);
                        System.out.println(this.getName() + " 醒了");
                        System.out.println(this.getName() + " end");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread("thread2"){
            public void run(){
                synchronized (mySynchronized){
                    System.out.println(this.getName()+" start");
                    System.out.println(this.getName()+" end");
                }
            }
        }.start();
    }

}
