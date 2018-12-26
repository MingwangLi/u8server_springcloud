package com.jmsht.u8server.admin.test;

/**
 * 多线程同步串行执行
 * 三个线程ABC 交替打印ABC
 */
public class ThreadCommunication implements Runnable{

    private String name;
    private Object prev;
    private Object self;

    private ThreadCommunication(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    public static void main(String[] args) throws Exception{
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadCommunication pa = new ThreadCommunication("A", c, a);
        ThreadCommunication pb = new ThreadCommunication("B", a, b);
        ThreadCommunication pc = new ThreadCommunication("C", b, c);

        //确保先启动pa 然后启动pb 最后启动pc
        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;
                    try{
                        Thread.sleep(1);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
