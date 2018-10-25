package com.jmsht.u8server.admin.service.impl;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.dao.UChannelDao;
import com.jmsht.u8server.admin.service.UChannelService;
import com.jmsht.u8server.admin.vo.UChannelVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UChannelServiceImpl implements UChannelService {

    @Autowired
    private UChannelDao uChannelDao;

    @Override
    public Page<UChannelVO> getChanelsWithPage(Map<String, Object> params, RowBounds rowBounds) {
        return uChannelDao.getChanelsWithPage(params, rowBounds);
    }
}
