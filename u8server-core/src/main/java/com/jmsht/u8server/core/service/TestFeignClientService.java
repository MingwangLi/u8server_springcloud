package com.jmsht.u8server.core.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "u8server", fallback = TestFeignClientService.HystrixClientFallback.class)
public interface TestFeignClientService {

    @RequestMapping("/admin/doLogin")
    String doLogin(@RequestParam("username") String username, @RequestParam("password") String password);

    @Component
    class HystrixClientFallback implements TestFeignClientService {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public String doLogin(String username, String password) {
            logger.info("远程调用:{} 发生异常，进入fallback方法，接收的参数：username = {},password={}", "/admin/doLogin", username, password);
            JSONObject json = new JSONObject();
            json.put("code",500);
            json.put("msg","远程调用失败");
            return json.toString();
        }

    }
}
