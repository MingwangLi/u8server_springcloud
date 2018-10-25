package com.jmsht.u8server.admin.web.controller;

import com.github.pagehelper.Page;
import com.jmsht.u8server.admin.domain.UAdmin;
import com.jmsht.u8server.admin.service.UAdminService;
import com.jmsht.u8server.admin.util.JQueryUIData;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UAdminService uAdminService;

    @RequestMapping("/adminRoleManage")
    public String toAdmins() {
        return "admins";
    }

    @RequestMapping("/getAllAdmins")
    @ResponseBody
    public JQueryUIData getAllAdmins(Integer page, Integer rows, HttpServletResponse response) {
        if (null == page) {
            page = 1;
        }
        if (null == rows) {
            rows = 20;
        }
        RowBounds rowBounds = new RowBounds(page, rows);
        Page<UAdmin> uAdminsWithPage = uAdminService.getUAdminsWithPage(rowBounds);
        // JSONObject object = new JSONObject();
        // object.put("total",uAdminsWithPage.getTotal());
        // object.put("rows",uAdminsWithPage.getResult());
        // //return object.toString();   乱码 String默认使用StringHttpMessageConverter转换，而它默认编码是ISO-8859-1
        // return object;      //ok
        return JQueryUIData.init(uAdminsWithPage);
    }
}
