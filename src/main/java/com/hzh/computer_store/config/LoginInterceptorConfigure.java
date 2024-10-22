package com.hzh.computer_store.config;

import com.hzh.computer_store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//注册拦截器
@Configuration //使用 @Configuration 标记类作为配置类替换 xml 配置文件 自动加载当前的类并进行拦截器的注册
public class LoginInterceptorConfigure implements WebMvcConfigurer {

    //添加并配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        //配置白名单存放在List集合
        //不需要登录就可以访问的资源
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/districts/**");
        patterns.add("/products/hot_list");

        //注册拦截器
        //addPathPatterns("/**")拦截对应的url,"/**",拦截所有请求
        //excludePathPatterns(patterns)添加白名单,参数为列表
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }

}
