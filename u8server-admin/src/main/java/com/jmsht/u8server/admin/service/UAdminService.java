package com.jmsht.u8server.admin.service;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.domain.UAdmin;
import org.apache.ibatis.session.RowBounds;

public interface UAdminService {

    UAdmin getUAdminByUserName(String username);

    void changePassword(Integer uid, String password);

    Page<UAdmin> getUAdminsWithPage(RowBounds rowBounds);
}
