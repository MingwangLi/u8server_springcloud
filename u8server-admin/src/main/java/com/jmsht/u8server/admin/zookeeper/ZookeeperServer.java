package com.jmsht.u8server.admin.zookeeper;

import org.apache.commons.lang3.StringUtils;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static String ROOT_NODE = "/thread_lock_root";
	// 根节点
	public static String SCHEDULE_ROOT_NODE = "/schedule_root";

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
	public ZookeeperServer(@Value("${zkServer}")String server) throws Exception {
		try {
			if (zk == null) {
				ZooKeeper zk2 = new ZooKeeper(server, 30000, new Mywatcher());
				// 根节点不存在，创建
				if (zk2.exists(ROOT_NODE, false) == null) {
					// 创建根节点
					zk2.create(ROOT_NODE, "lock_root_node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				zk = zk2;
				logger.debug("zookeeper 连接成功，server:{}", server);

			}
		} catch (IOException | KeeperException | InterruptedException e) {
			zk = null;
			// e.printStackTrace();
			logger.warn("zookeeper 连接失败，server:{}， errorMsg:{}", server, e.getMessage());
			throw e;
		}
	}

	public synchronized void zkReconnect() {
		close();
		try {
			connect();
		} catch (IOException e) {
		}
	}
	public synchronized void close() {
		if (zk != null) {
			try {
				zk.close();
			} catch (InterruptedException e) {
			}
			zk = null;
		}
	}
	private synchronized void connect() throws IOException {
		if (zk == null  && !StringUtils.isBlank(server))
			zk = new ZooKeeper(server, 5000, null);
	}


}

class Mywatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.getState());
	}

}
