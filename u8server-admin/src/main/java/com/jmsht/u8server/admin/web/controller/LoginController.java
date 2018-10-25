package com.jmsht.u8server.admin.web.controller;

import com.jmsht.u8server.admin.annotation.LogAnnotaion;
import com.jmsht.u8server.admin.constant.CommonConstant;
import com.jmsht.u8server.admin.domain.Result;
import com.jmsht.u8server.admin.domain.UAdmin;
import com.jmsht.u8server.admin.service.UAdminService;
import com.jmsht.u8server.admin.util.Md5Encrypter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UAdminService uAdminService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        logger.info("----去登陆");
        return "login";
    }

    //@apiGroup中文乱码 这里定义一个变量来解决 注意@apiGroup唯一
    /**
     * @apiDefine UserGroup 用户模块
     */


    /**
     * @api {POST}  /admin/doLogin  登陆
     * @apiGroup UserGroup
     * @apiName 登陆
     * @apiDescription 用户登陆接口
     * @apiVersion 1.0.0
     * @apiParam {String} username        用户名
     * @apiParam {String} password    密码
     * @apiParamExample {Object}  args-Example
     * {
     * "username":"jmsht_game",
     * "password":"jmsht1405"
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "code": 200,
     * "msg": "登陆成功"
     * }
     * @apiErrorExample Error-Response
     * HTTP/1.1
     * {
     * "code": 201,
     * "msg":"密码错误"
     * }
     * @apiSampleRequest http://127.0.0.1:8080/admin/doLogin
     */
    @LogAnnotaion
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(String username, String password, HttpServletRequest request) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                result.setCode(CommonConstant.PARAMERROR);
                result.setMsg("参数错误");
                return result;
            }
            UAdmin uAdmin = uAdminService.getUAdminByUserName(username);
            if (null == uAdmin) {
                result.setCode(CommonConstant.PARAMERROR);
                result.setMsg("用户名不存在");
                return result;
            }
            if (CommonConstant.FORBIDDEN == uAdmin.getPermission()) {
                result.setCode(CommonConstant.ILLEGAL);
                result.setMsg("该用户已经被禁用");
                return result;
            }
            password = Md5Encrypter.MD5(password);
            if (!password.equals(uAdmin.getPassword())) {
                result.setCode(CommonConstant.PARAMERROR);
                result.setMsg("密码错误");
                return result;
            }
            result.setCode(CommonConstant.SUCCESS);
            result.setMsg("登陆成功");
            HttpSession session = request.getSession();
            session.setAttribute("UAdmin", uAdmin);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("----登陆异常,异常信息:{}", e.getMessage());
            result.setCode(CommonConstant.PROGRAMERROR);
            result.setMsg("登陆异常");
            return result;
        }
    }

    @RequestMapping("/exit")
    @ResponseBody
    public Result exit(HttpServletRequest request) {
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            result.setCode(CommonConstant.SUCCESS);
            result.setMsg("退出成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("----退出异常,异常信息:{}", e.getMessage());
            result.setCode(CommonConstant.PROGRAMERROR);
            result.setMsg("退出异常");
            return result;
        }
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Result changePassword(String password, HttpServletRequest request) {
        Result result = new Result();
        try {
            password = Md5Encrypter.MD5(password);
            HttpSession session = request.getSession();
            UAdmin admin = (UAdmin) session.getAttribute("UAdmin");
            Integer uid = admin.getId();
            uAdminService.changePassword(uid, password);
            result.setCode(CommonConstant.SUCCESS);
            result.setMsg("修改密码成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("----修改密码异常,异常信息:{}", e.getMessage());
            result.setCode(CommonConstant.PROGRAMERROR);
            result.setMsg("修改密码失败");
            return result;
        }
    }

}
