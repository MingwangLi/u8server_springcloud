package com.jmsht.u8server.admin.service;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.vo.UChannelVO;
import org.apache.ibatis.session.RowBounds;
import java.util.Map;

public interface UChannelService {

    Page<UChannelVO> getChanelsWithPage(Map<String, Object> params, RowBounds rowBounds);
}
