package com.jmsht.u8server.core.zookeeper;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ZookeeperServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //根节点
    public static String ROOT_NODE = "/thread_lock_root";

    public static ZooKeeper zk;

    @Value("${zkServer}")
    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public ZookeeperServer() {

    }

    @Autowired
    public ZookeeperServer(@Value("${zkServer}")String server) throws Exception{
        try {
            if (null == zk) {
                ZooKeeper zookeeper = new ZooKeeper(server,30000,new Mywatcher());
                if (null == zookeeper.exists(ROOT_NODE,false)) {
                    zookeeper.create(ROOT_NODE,"lock_root_node".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                zk = zookeeper;
                logger.debug("zookeeper 连接成功，server:{}",server);
            }
        } catch (IOException | KeeperException | InterruptedException error) {
            error.printStackTrace();
            zk = null;
            logger.warn("zookeeper 连接失败，server:{}， errorMsg:{}",server,error.getMessage());
            throw error;
        }
    }

    public synchronized void reConnect() {
        close();
        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void connect() throws IOException {
        if (zk == null && StringUtils.isNotEmpty(server)) {
            zk = new ZooKeeper(server,5000,null);
        }
    }

    public synchronized void close() {
        if (null != zk) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zk = null;
        }
    }
}


class Mywatcher implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.getState());
    }

}
