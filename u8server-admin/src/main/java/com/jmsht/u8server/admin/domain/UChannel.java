package com.jmsht.u8server.admin.domain;

import java.io.Serializable;

public class UChannel implements Serializable {

    private Integer id;
    private Integer channelID;
    private Integer appID;
    private String cpAppID;
    private String cpAppKey;
    private String cpAppSecret;
    private String cpID;
    private String cpPayID;
    private String cpPayKey;
    private String cpPayPriKey;
    private Integer masterID;
    private String cpConfig;
    private String authUrl;
    private String payCallbackUrl;
    private String orderUrl;
    private String verifyClass;
    private Integer openPayFlag;
    private Integer platID;
    private String chargeCloseTime;
    private Integer version;
    private String lastVersionUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public Integer getAppID() {
        return appID;
    }

    public void setAppID(Integer appID) {
        this.appID = appID;
    }

    public String getCpAppID() {
        return cpAppID;
    }

    public void setCpAppID(String cpAppID) {
        this.cpAppID = cpAppID;
    }

    public String getCpAppKey() {
        return cpAppKey;
    }

    public void setCpAppKey(String cpAppKey) {
        this.cpAppKey = cpAppKey;
    }

    public String getCpAppSecret() {
        return cpAppSecret;
    }

    public void setCpAppSecret(String cpAppSecret) {
        this.cpAppSecret = cpAppSecret;
    }

    public String getCpID() {
        return cpID;
    }

    public void setCpID(String cpID) {
        this.cpID = cpID;
    }

    public String getCpPayID() {
        return cpPayID;
    }

    public void setCpPayID(String cpPayID) {
        this.cpPayID = cpPayID;
    }

    public String getCpPayKey() {
        return cpPayKey;
    }

    public void setCpPayKey(String cpPayKey) {
        this.cpPayKey = cpPayKey;
    }

    public String getCpPayPriKey() {
        return cpPayPriKey;
    }

    public void setCpPayPriKey(String cpPayPriKey) {
        this.cpPayPriKey = cpPayPriKey;
    }

    public Integer getMasterID() {
        return masterID;
    }

    public void setMasterID(Integer masterID) {
        this.masterID = masterID;
    }

    public String getCpConfig() {
        return cpConfig;
    }

    public void setCpConfig(String cpConfig) {
        this.cpConfig = cpConfig;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getVerifyClass() {
        return verifyClass;
    }

    public void setVerifyClass(String verifyClass) {
        this.verifyClass = verifyClass;
    }

    public Integer getOpenPayFlag() {
        return openPayFlag;
    }

    public void setOpenPayFlag(Integer openPayFlag) {
        this.openPayFlag = openPayFlag;
    }

    public Integer getPlatID() {
        return platID;
    }

    public void setPlatID(Integer platID) {
        this.platID = platID;
    }

    public String getChargeCloseTime() {
        return chargeCloseTime;
    }

    public void setChargeCloseTime(String chargeCloseTime) {
        this.chargeCloseTime = chargeCloseTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getLastVersionUrl() {
        return lastVersionUrl;
    }

    public void setLastVersionUrl(String lastVersionUrl) {
        this.lastVersionUrl = lastVersionUrl;
    }
}
