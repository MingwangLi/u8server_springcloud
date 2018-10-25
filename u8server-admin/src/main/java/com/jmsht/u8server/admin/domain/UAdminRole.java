package com.jmsht.u8server.admin.domain;

import java.io.Serializable;
import java.util.Date;

public class UAdminRole implements Serializable {

    private Integer id;
    private Date createTime;
    private Integer creatorID;
    private String permission;
    private String roleDesc;
    private String roleName;
    private Integer topRole;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(Integer creatorID) {
        this.creatorID = creatorID;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getTopRole() {
        return topRole;
    }

    public void setTopRole(Integer topRole) {
        this.topRole = topRole;
    }
}
