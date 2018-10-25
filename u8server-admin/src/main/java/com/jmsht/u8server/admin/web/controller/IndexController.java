package com.jmsht.u8server.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jmsht.u8server.admin.constant.CommonConstant;
import com.jmsht.u8server.admin.domain.UAdmin;
import com.jmsht.u8server.admin.domain.UAdminRole;
import com.jmsht.u8server.admin.domain.USysMenu;
import com.jmsht.u8server.admin.service.UAdminRoleService;
import com.jmsht.u8server.admin.service.USysMenuService;
import com.jmsht.u8server.admin.vo.USysMenuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UAdminRoleService uAdminRoleService;

    @Autowired
    private USysMenuService uSysMenuService;

    @RequestMapping("/index")
    public String index() {
        logger.info("----去首页");
        return "index";
    }

    @RequestMapping("/getAdminNameAndRoleName")
    @ResponseBody
    public JSONObject getAdminNameAndRoleName(HttpServletRequest request) {
        UAdmin uAdmin = (UAdmin) request.getSession().getAttribute("UAdmin");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("adminName", uAdmin.getUsername());
        jsonObject.put("adminRoleName", uAdmin.getAdminRoleName());
        return jsonObject;
    }

    @RequestMapping("/getMyMenus")
    @ResponseBody
    public List<USysMenuVO> getMyMenus(HttpServletRequest request) {
        UAdmin uAdmin = (UAdmin) request.getSession().getAttribute("UAdmin");
        Integer adminRoleID = uAdmin.getAdminRoleID();
        UAdminRole uAdminRole = uAdminRoleService.getUAdminRoleByID(adminRoleID);
        String permission = uAdminRole.getPermission();
        String[] menuIds = permission.split(",");
        List<USysMenu> parentUSysMenus = new ArrayList<>();
        List<USysMenuVO> USysMenuVOs = new ArrayList<>();
        for (String menuId : menuIds) {
            USysMenu uSysMenu = uSysMenuService.getSysMenuById(Integer.parseInt(menuId));
            if (CommonConstant.PARENTMENU == uSysMenu.getParentID()) {
                parentUSysMenus.add(uSysMenu);
            }
        }
        for (USysMenu uSysMenu : parentUSysMenus) {
            USysMenuVO uSysMenuVO = new USysMenuVO();
            uSysMenuVO.setId(uSysMenu.getId());
            uSysMenuVO.setName(uSysMenu.getName());
            List<USysMenu> uSysMenus = uSysMenuService.getSysMenuByParentId(uSysMenu.getId());
            uSysMenuVO.setChildMenus(uSysMenus);
            USysMenuVOs.add(uSysMenuVO);
        }
        return USysMenuVOs;
    }
}
