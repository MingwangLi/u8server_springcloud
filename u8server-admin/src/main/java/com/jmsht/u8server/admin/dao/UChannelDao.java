package com.jmsht.u8server.admin.dao;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.vo.UChannelVO;
import org.apache.ibatis.session.RowBounds;
import java.util.Map;

public interface UChannelDao {

    Page<UChannelVO> getChanelsWithPage(Map<String, Object> params, RowBounds rowBounds);
}
