package com.ruoyi.config;

import com.ruoyi.interceptor.AdminRequestInterceptor;
import com.ruoyi.interceptor.LoginInterceptor;
import com.ruoyi.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Autowired
    private AdminRequestInterceptor adminRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册requestInterceptor拦截器
        InterceptorRegistration request = registry.addInterceptor(requestInterceptor);
        //所有路径都被拦截
        request.addPathPatterns("/rc-api/**");

        //注册loginInterceptor拦截器
        InterceptorRegistration login = registry.addInterceptor(loginInterceptor);
        //所有路径都被拦截
        login.addPathPatterns("/rc-api/**");
        //添加不拦截路径
        login.excludePathPatterns("/rc-api/user/**");
        login.excludePathPatterns("/rc-api/digital/**");

        //注册adminRequestInterceptor拦截器
        InterceptorRegistration adminRequest = registry.addInterceptor(adminRequestInterceptor);
        adminRequest.addPathPatterns("/rc-api/**", "/system/**");
    }

}
