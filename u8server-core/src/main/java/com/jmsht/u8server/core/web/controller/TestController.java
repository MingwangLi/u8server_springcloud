package com.jmsht.u8server.core.web.controller;

import com.jmsht.u8server.core.service.TestFeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private TestFeignClientService testFeignClientService;


    @RequestMapping("/testFeignClient")
    @ResponseBody
    public String testFeignClient(String username, String password) {
        return testFeignClientService.doLogin(username, password);
    }
}
