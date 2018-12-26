package com.jmsht.u8server.core.zookeeper;

import com.jmsht.u8server.core.zookeeper.local.ZkLocalLock;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ZookeeperDisLock {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rootNode = ZookeeperServer.ROOT_NODE;

    public static String DEEFAULT_NODE = "distributed_thread_lock";

    private String key;

    public String nodeName;

    private Watcher watcher;

    private static ZooKeeper zooKeeper;

    public Integer notifyCount = 0;

    /**
     * 默认使用分布式锁 分布式锁失效 使用本地锁
     */
    private Boolean zkFlag = true;

    private ZkLocalLock zkLocalLock;


    public ZookeeperDisLock(String key) {
        this.key = key;
        zkFlag = false;
        try {
            zkLocalLock = ZkLocalLock.getInstance(key);
            watcher = new TaskLockWatch(this);
            zooKeeper = ZookeeperServer.zk;
            if (null != zooKeeper) {
                logger.debug("使用分布式线程锁,key:{}",key);
                zkFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("使用本地线程锁,key:{}",key);
        }
    }

    public void lock() {
        //zkLocalLock.lock();  //key是业务+userID  如 sale_userID   buy_userID 对统一用户线程锁定
        if (zkFlag) {
            try {
                if (null == zooKeeper.exists(parentNode(),null)) {
                    zooKeeper.create(parentNode(),"lock_root_node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  //根节点下业务子节点
                }
                String node = parentNode() + "/lock_";
                //业务子节点下子节点 分布式线程锁实现的核心 加锁(创建子节点) 释放锁(删除子节点) 串行执行
                this.nodeName = zooKeeper.create(node, (System.currentTimeMillis() + "-task").getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL); //业务子节点下创建子节点
                while (true) {   //防止创建子节点失败
                    if (null == this.nodeName || node.equals(this.nodeName)) {
                        Thread.sleep(50);  //线程休眠 方式zk负载过高
                        this.nodeName = zooKeeper.create(node, (System.currentTimeMillis() + "-task").getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
                    }else {
                        break;
                    }
                }
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
                logger.error("使用分布式线程锁创建业务子节点异常,异常信息:{}",e.getMessage());
            }
            getLock();
        }
    }

    /**
     * 由于不确定最小节点删除的时机 需要递归
     */
    @SuppressWarnings("all")
    private void getLock() {
        try {
            List<String> children = zooKeeper.getChildren(parentNode(), false);
            String miniNode = parentNode() + "/" + getMiniNodeName(children);
            if (!miniNode.equals(nodeName)) {             //分布式线程锁核心判断条件 其他线程还没有执行完(删除子节点) 当前线程需要wait
                //      * If the watch is non-null and the call is successful (no exception is thrown),
                //      * a watch will be left on the node with the given path. The watch will be
                //      * triggered by a successful operation that creates/delete the node or sets
                //      * the data on the node.
                // 如果watcher不为null 并且正常返回  watcher会成功绑定并监视当前读取的最小节点
                Stat stat = zooKeeper.exists(miniNode, watcher);

                if (null != stat) {
                   // synchronized (this) {
                   //      this.wait();       盲目等待?? 假如Watcher已经执行notify 等到天荒地老 海枯石烂     程(ai)序(qing)最怕的是盲目等待 此处也可以无限getLock();  浪费cpu资源
                   //      getLock();
                   //  }
                    boolean miniNodeNotifyAlready  = false;
                    //此处只有不同用户线程和分布式系统形同用户线程 synchronized要怎么锁

                    synchronized (this) {            //这个synchronized的作用???  防止本地线程失效 或者是作者忘了外层有一个本地线程锁 注释本地线程锁测试 完全ok 如果本地线程真的失效 而使用同一个ZookeeperDisLock 不加锁会导致所有线程wait() 假设notifyCount>0 通知
                        // 已发出 本来需要递归即可 但是在miniNodeNotifyAlready写入之前 其他线程读取miniNodeNotifyAlready还是false 后面线程全部进入wait状态 永远不会被唤醒 死锁
                        //经验 线程的notify、wait(判断条件有关的读写)必须在synchronized中避免多线程死锁
                        if (notifyCount != 0) {      //Watcher已执行  假如notify()在代码这里之前执行了 也没有关系  递归判断下!miniNode.equals(nodeName) 必定为false 完美
                            miniNodeNotifyAlready = true;
                        }
                        if (!miniNodeNotifyAlready) {    //Watcher未执行 当前线程wait 等待唤醒递归判断!miniNode.equals(nodeName) 必定为false
                            this.wait();
                            getLock();
                        }
                    }
                    if (miniNodeNotifyAlready) {           //Watcher已执行 递归调用判断即可
                        notifyCount = 0;
                        getLock();
                    }
                }else {
                    getLock(); //递归
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            logger.error("使用分布式线程锁获取锁异常,异常信息:{}",e.getMessage());
        }
    }

    public void unlock() {
        if (zkFlag) {
            try {
                zooKeeper.delete(nodeName,-1);
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
                logger.error("使用分布式线程锁释放锁异常,异常信息:{}",e.getMessage());
            }
        }
        //zkLocalLock.unlock();
    }

    @SuppressWarnings("all")
    public boolean tryLock() {
        boolean a = zkLocalLock.tryLock();
        boolean res = false;
        if (a) {
            if (zkFlag) {
                try {
                    if (zooKeeper.exists(parentNode(), false) == null) {
                        zooKeeper.create(parentNode(), "lock_root_node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                    String node = parentNode() + "/lock_";
                    this.nodeName = zooKeeper.create(node, (System.currentTimeMillis() + "-task").getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    List<String> list = zooKeeper.getChildren(parentNode(), false);
                    String miniNode = parentNode() + "/" + getMiniNodeName(list);
                    if (!this.nodeName.equals(miniNode)) {
                        Stat stat = zooKeeper.exists(miniNode, watcher);
                        // 最小节点是否状态正常
                        if (stat != null) {// 最小节点
                            list = zooKeeper.getChildren(parentNode(), false);
                            miniNode = parentNode() + "/" + getMiniNodeName(list);
                            if (this.nodeName.equals(miniNode)) {
                                res = true;
                            } else {
                                zooKeeper.delete(this.nodeName, -1);
                                res = false;
                            }
                        } else {
                            zooKeeper.delete(this.nodeName, -1);
                            res = false;
                        }
                    } else {
                        res = true;
                    }
                } catch (KeeperException | InterruptedException e) {
                    try {
                        zooKeeper.delete(this.nodeName, -1);
                    } catch (InterruptedException | KeeperException e1) {
                        e1.printStackTrace();
                    }
                    res = false;
                }
                if(res == false){
                    zkLocalLock.unlock();
                }
                return res;
            }
        }
        return a;
    }

    private String parentNode() {
        return rootNode+"/"+StringUtils.defaultIfEmpty(key,DEEFAULT_NODE);
    }

    // 获取节点列表中最小节点
    private String getMiniNodeName(List<String> children) {
        String args[] = new String[children.size()];
        children.toArray(args);
        Arrays.sort(args);
        return args[0];

    }


}
