package com.jmsht.u8server.admin.service.impl;

import com.jmsht.u8server.admin.dao.USysMenuDao;
import com.jmsht.u8server.admin.domain.USysMenu;
import com.jmsht.u8server.admin.service.USysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class USysMenuServiceImpl implements USysMenuService {

    @Autowired
    private USysMenuDao uSysMenuDao;

    @Override
    public USysMenu getSysMenuById(Integer id) {
        return uSysMenuDao.getSysMenuById(id);
    }

    @Override
    public List<USysMenu> getSysMenuByParentId(Integer parentId) {
        return uSysMenuDao.getSysMenuByParentId(parentId);
    }
}
