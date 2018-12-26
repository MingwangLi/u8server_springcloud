package com.jmsht.u8server.admin.component;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jmsht.u8server.admin.web.interceptor.PrivilegeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("com.jmsht.u8server.admin.web.controller")  //扫描Controller
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private PrivilegeInterceptor privilegeInterceptor;

    /**
     * 配置默认首页
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/static/view/login.html");
        //registry.addViewController("/").setViewName("/admin/toLogin");  // /请求拦截  /admin/toLogin不拦截
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }


    /**
     * 配置 视图解析器  对jsp不友好  强烈建议用html
     *
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/static/view/"); // 运行时的目录结构
        viewResolver.setSuffix(".html");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }


    /**
     * 静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**") // 对外暴露的访问路径
                .addResourceLocations("classpath:/static/"); // 文件放置的目录
    }


    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 添加拦截url，     excludePathPatterns 排除拦截url
        registry.addInterceptor(privilegeInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/toLogin","/admin/doLogin");
        super.addInterceptors(registry);
    }

    //这种方式没用
   /* @Bean
    public HttpMessageConverter<String> responseBodyStringConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return converter;
    }*/

    /**
     * 修改StringHttpMessageConverter默认配置
     * @param converters
     */
   /* @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(responseBodyStringConverter());
    }*/


    // TODO: 2018/10/19 很多这个警告 一直重复 WARN org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver:136 org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation
    // TODO: 2018/10/19 返回String中文有""

    /**
     * 利用fastjson替换掉jackson，解决非JSON的中文String乱码问题 强烈建议所有的接口设计返回JSON 这是一种规范
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();   //fastjson1.2.4没有这个  更换到1.2.14
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }

}

