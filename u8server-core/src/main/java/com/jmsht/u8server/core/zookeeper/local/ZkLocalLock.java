package com.jmsht.u8server.core.zookeeper.local;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZkLocalLock {

    private static Map<String,ZkLocalLock> instance = new HashMap<>();       //考虑是否用ConcurrentHashMap更合理

    private Lock lock;

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    private ZkLocalLock() {
        lock = new ReentrantLock();
    }

    @SuppressWarnings("all")
    public static ZkLocalLock getInstance(String key) {
        //双重检查锁定
        if (!instance.containsKey(key)) {
            synchronized (key) {
                if (!instance.containsKey(key)) {
                    ZkLocalLock zkLocalLock = new ZkLocalLock();
                    instance.put(key,zkLocalLock);
                }
            }
        }
        return instance.get(key);
    }

    public void lock() {
        this.lock.lock();
    }

    public boolean tryLock() {
        return this.lock.tryLock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public void lockInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }
}
