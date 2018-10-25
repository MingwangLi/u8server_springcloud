package com.jmsht.u8server.admin.service.impl;

import com.jmsht.u8server.admin.dao.UAdminRoleDao;
import com.jmsht.u8server.admin.domain.UAdminRole;
import com.jmsht.u8server.admin.service.UAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UAdminRoleServiceImpl implements UAdminRoleService {

    @Autowired
    private UAdminRoleDao uAdminRoleDao;

    @Override
    public UAdminRole getUAdminRoleByID(Integer id) {
        return uAdminRoleDao.getUAdminRoleByID(id);
    }
}
