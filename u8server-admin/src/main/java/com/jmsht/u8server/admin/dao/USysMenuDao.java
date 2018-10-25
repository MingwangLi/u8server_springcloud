package com.jmsht.u8server.admin.dao;


import com.jmsht.u8server.admin.domain.USysMenu;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface USysMenuDao {

    USysMenu getSysMenuById(@Param("id") Integer id);

    List<USysMenu> getSysMenuByParentId(@Param("parentId") Integer parentId);
}
