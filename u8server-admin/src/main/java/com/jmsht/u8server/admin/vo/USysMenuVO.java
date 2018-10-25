package com.jmsht.u8server.admin.vo;

import com.jmsht.u8server.admin.domain.USysMenu;
import java.io.Serializable;
import java.util.List;

public class USysMenuVO implements Serializable {

    private Integer id;
    private String name;
    private List<USysMenu> childMenus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<USysMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<USysMenu> childMenus) {
        this.childMenus = childMenus;
    }
}
