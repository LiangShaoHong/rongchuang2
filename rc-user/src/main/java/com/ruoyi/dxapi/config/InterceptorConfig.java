package com.ruoyi.dxapi.config;

import com.ruoyi.dxapi.interceptor.AdminRequestInterceptor;
import com.ruoyi.dxapi.interceptor.LoginInterceptor;
import com.ruoyi.dxapi.interceptor.RequestInterceptor;
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
        request.addPathPatterns("/dx-api/**");                   //所有路径都被拦截

        //注册loginInterceptor拦截器
        InterceptorRegistration login = registry.addInterceptor(loginInterceptor);
        login.addPathPatterns("/dx-api/**");                   //所有路径都被拦截
        login.excludePathPatterns("/dx-api/base/**");          //添加不拦截路径

        //注册adminRequestInterceptor拦截器
        InterceptorRegistration adminRequest = registry.addInterceptor(adminRequestInterceptor);
        adminRequest.addPathPatterns("/dx/**", "/system/**");
    }

}
