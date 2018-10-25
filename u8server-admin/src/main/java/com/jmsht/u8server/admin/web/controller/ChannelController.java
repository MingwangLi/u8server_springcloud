package com.jmsht.u8server.admin.web.controller;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.service.UChannelService;
import com.jmsht.u8server.admin.util.JQueryUIData;
import com.jmsht.u8server.admin.vo.UChannelVO;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/channels")
public class ChannelController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UChannelService uChannelService;

    @RequestMapping("/channelManage")
    public String getChannels() {
        return "channels";
    }

    @RequestMapping("/getAllChannels")
    @ResponseBody
    public JQueryUIData getAllChannels(Integer page, Integer rows, String searchMaserName, String channelID, String searchGameName) {
        if (null == page) {
            page = 1;
        }
        if (null == rows) {
            rows = 20;
        }
        RowBounds rowBounds = new RowBounds(page, rows);
        Map<String, Object> params = new HashMap<>();
        params.put("channelID", channelID);
        //这里省去searchMaserName searchGameName的模糊搜索 该项目主要用于搭建技术脚手架
        Page<UChannelVO> chanelsWithPage = uChannelService.getChanelsWithPage(params, rowBounds);
        List<UChannelVO> uChannelList = chanelsWithPage.getResult();
        for (UChannelVO uChannelVO : uChannelList) {
            //关联查询游戏名称和渠道商 省略
            uChannelVO.setAppName("test01");
            uChannelVO.setMasterName("test02");
        }
        return JQueryUIData.init(chanelsWithPage);
    }
}
