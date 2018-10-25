package com.jmsht.u8server.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

//@SpringBootApplication 默认扫描java路径下所有类   scanBasePackages指定扫描路径 此处特意没有扫描controller 让WebMvcConfig扫描controller 虽然在这里扫描也行 但是感觉让WebMvcConfig去扫描更规范 另外如果重复扫描会有警告
@SpringBootApplication(scanBasePackages = {"com.jmsht.u8server.core"})
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients
public class U8serverApplication {

    public static void main(String[] args) {
        SpringApplication.run(U8serverApplication.class, args);
    }

}
