package com.jmsht.u8server.core.web.controller;

import com.jmsht.u8server.core.service.TestFeignClientService;
import com.jmsht.u8server.core.zookeeper.ZookeeperDisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    //private ZookeeperDisLock zookeeperDisLockCommon = new ZookeeperDisLock("testWithOutZkLocalLock");  //为什么这里没有成功创建对象

    private ZookeeperDisLock zookeeperDisLockCommon;

    @Autowired
    private TestFeignClientService testFeignClientService;


    @RequestMapping("/testFeignClient")
    @ResponseBody
    public String testFeignClient(String username, String password) {
        return testFeignClientService.doLogin(username, password);
    }


    @SuppressWarnings("all")
    @RequestMapping("/test03")
    @ResponseBody
    public String testZookeeperDisLock03() {
        ZookeeperDisLock zookeeperDisLock = new ZookeeperDisLock("looklock03");
        zookeeperDisLock.lock();
        try {
            Thread.sleep(30000);       //模拟业务
            return "success";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        } finally {
            //zookeeperDisLock.unlock();       //测试不释放锁 导致admin模块被永久阻塞
        }
    }

    /**
     * 注释本地线程锁测试
     * @return
     */
    @SuppressWarnings("all")
    @RequestMapping("/test04")
    @ResponseBody
    public String testZookeeperDisLock04() {
        zookeeperDisLockCommon = new ZookeeperDisLock("testWithOutZkLocalLock");
        zookeeperDisLockCommon.lock();
        try {
            Thread.sleep(30000);       //模拟业务
            return "success";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        } finally {
            //zookeeperDisLockCommon.unlock();       //注释本地线程锁测试 测试不释放锁 是否导致testZookeeperDisLock05阻塞
        }
    }

    /**
     * 注释本地线程锁测试
     * @return
     */
    @SuppressWarnings("all")
    @RequestMapping("/test05")
    @ResponseBody
    public String testZookeeperDisLock05() {
        zookeeperDisLockCommon.lock();
        try {
            Thread.sleep(30000);       //模拟业务
            return "success";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        } finally {
            zookeeperDisLockCommon.unlock();
        }
    }

    //测试结果
    //如果不注释本地线程锁 只创建一个节点 testZookeeperDisLock05被本地线程锁阻塞没有创建节点 删除testZookeeperDisLock04创建的节点 由于testZookeeperDisLock05未创建节点和注册Watcher 一直被阻塞
    //如果注释本地线程锁 创建两个节点 testZookeeperDisLock04 没有释放锁删除节点 导致testZookeeperDisLock05被阻塞 删除testZookeeperDisLock04创建的节点 testZookeeperDisLock05注册的Watcher
    //唤醒zookeeperDisLockCommon上wait的线程 删除子节点

}
