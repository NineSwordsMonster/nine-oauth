package com.nineswords.oauth.config.view;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create by Jarvis.wang on Jarvis's device
 *
 * @author Jarvis.wang  Jarvis
 * @date 2018-12-28 10:15
 */
@Configuration
public class WebjarsConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置映射关系
        //即：/webjars/** 映射到 classpath:/META-INF/resources/webjars/
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                //新增 resourceChain 配置即开启缓存配置。
                .resourceChain(true);
    }
}
