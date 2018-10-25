package com.jmsht.u8server.admin.vo;

import com.jmsht.u8server.admin.domain.UChannel;

public class UChannelVO extends UChannel {

    //这里的两个属性完全可以写在UChannel里 但是domain中的实体类对应数据库表 VO数据返回给视图层 DTO数据封装传输更符合规范
    private String appName;
    private String masterName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
}
