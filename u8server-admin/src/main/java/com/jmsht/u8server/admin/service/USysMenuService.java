package com.jmsht.u8server.admin.service;

import com.jmsht.u8server.admin.domain.USysMenu;
import java.util.List;

public interface USysMenuService {

    USysMenu getSysMenuById(Integer id);

    List<USysMenu> getSysMenuByParentId(Integer parentId);
}
