package com.jmsht.u8server.admin.dao;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.domain.UAdmin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UAdminDao {

    UAdmin getUAdminByUserName(@Param("username") String username);

    void changePassword(@Param("uid") Integer uid, @Param("password") String password);

    Page<UAdmin> getUAdminsWithPage(RowBounds rowBounds);
}
