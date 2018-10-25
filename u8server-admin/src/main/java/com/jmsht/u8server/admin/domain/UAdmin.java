package com.jmsht.u8server.admin.domain;

import java.io.Serializable;

public class UAdmin implements Serializable {

    private Integer id;
    private Integer adminRoleID;
    private String password;
    private String username;
    private String adminRoleName;
    private String adminGames;
    private Integer permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminRoleID() {
        return adminRoleID;
    }

    public void setAdminRoleID(Integer adminRoleID) {
        this.adminRoleID = adminRoleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdminRoleName() {
        return adminRoleName;
    }

    public void setAdminRoleName(String adminRoleName) {
        this.adminRoleName = adminRoleName;
    }

    public String getAdminGames() {
        return adminGames;
    }

    public void setAdminGames(String adminGames) {
        this.adminGames = adminGames;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}
