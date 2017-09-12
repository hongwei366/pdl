package com.jsjy.bigdata.lock;


public class MySynchronizedReadWrite {

    public static void main(String[] args) {
        final MySynchronizedReadWrite test = new MySynchronizedReadWrite();

        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    test.get(Thread.currentThread());
                }
            }.start();
        }


    }

    private synchronized void get(Thread thread) {
        long start = System.currentTimeMillis();
        int i = 0;
        while (System.currentTimeMillis() - start <= 1) {
            i++;
            if (i % 4 == 0) {
                System.out.println(thread.getName() + "正在进行写操作");
            } else {
                System.out.println(thread.getName() + "正在进行读操作");
            }
            System.out.println(thread.getName() + "读写操作完毕");
        }

    }

}
