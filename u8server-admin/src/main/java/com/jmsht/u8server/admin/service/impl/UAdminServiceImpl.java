package com.jmsht.u8server.admin.service.impl;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.dao.UAdminDao;
import com.jmsht.u8server.admin.domain.UAdmin;
import com.jmsht.u8server.admin.service.UAdminService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UAdminServiceImpl implements UAdminService {

    @Autowired
    private UAdminDao uAdminDao;

    @Override
    public UAdmin getUAdminByUserName(String username) {
        return uAdminDao.getUAdminByUserName(username);
    }

    @Override
    @Transactional
    public void changePassword(Integer uid, String password) {
        uAdminDao.changePassword(uid, password);
    }

    @Override
    public Page<UAdmin> getUAdminsWithPage(RowBounds rowBounds) {
        return uAdminDao.getUAdminsWithPage(rowBounds);
    }
}
