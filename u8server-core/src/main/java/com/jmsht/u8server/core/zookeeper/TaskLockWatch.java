package com.jmsht.u8server.core.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class TaskLockWatch implements Watcher {

    ZookeeperDisLock zookeeperDisLock;

    TaskLockWatch(ZookeeperDisLock zookeeperDisLock) {
        this.zookeeperDisLock = zookeeperDisLock;
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.EventType.NodeDeleted.equals(event.getType())) {
            synchronized (zookeeperDisLock){
                zookeeperDisLock.notifyCount++;  //与下面notify同步 可以保证notifCount作为notify()通知是否发出的准确条件 避免死锁
                zookeeperDisLock.notify();  //删除子节点成功 唤醒正在同步等待线程
            }
        }
    }
}
