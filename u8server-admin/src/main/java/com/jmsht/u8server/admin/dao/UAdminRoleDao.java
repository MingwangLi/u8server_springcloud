package com.jmsht.u8server.admin.dao;

import com.jmsht.u8server.admin.domain.UAdminRole;
import org.apache.ibatis.annotations.Param;

public interface UAdminRoleDao {

    UAdminRole getUAdminRoleByID(@Param("id") Integer id);
}
