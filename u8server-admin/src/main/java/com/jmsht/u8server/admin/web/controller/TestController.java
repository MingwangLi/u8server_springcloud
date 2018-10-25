package com.jmsht.u8server.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("/test01")
    @ResponseBody
    //如果返回String类型 中文会乱码 因为处理Stirng类型使用的字符集是iso-8859-1(StringHttpMessageConverter) 会用iso-8859-1解码 utf-8编码
    public String testStringEncoding() throws Exception {
        String msg = "求不乱码";
        //msg = new String(msg.getBytes("UTF-8"),"ISO-8859-1");  虽然这里用utf-8解码 iso-8859-1编码  但是返回的不是中文 StringHttpMessageConverter没有转换 依旧乱码 已在WebMvcConfig配置解决
        return msg;
    }

}
