package com.ruoyi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisService;

import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.user.domain.RcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisService redisService;


    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * return false时，被请求时，拦截器执行到此处将不会继续操作
     * return true时，请求将会继续执行后面的操作
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            // 获取请求的token
            String token = request.getHeader(Constants.X_TOKEN);

            if (!StringUtils.isEmpty(token)) {

                // 获取账号
                String platAccount = JWTUtil.getUsername(token);
                String userKey = Constants.DB_USER + platAccount;

                if (redisService.exists(userKey, Constants.DB_USER)) {

                    // 获取用户信息
                    RcUser user = (RcUser) redisService.get(userKey, Constants.DB_USER);
                    if (user != null) {
                        String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
                        if (redisService.exists(tokenKey, Constants.DB_USER)) {
                            String oldToken = (String) redisService.get(tokenKey, Constants.DB_USER);
                            // 比对token
                            if (oldToken.equals(token)) {
                                return true;
                            }
                        }
                    }
                }
            }

            // 设置服务器端编码
            response.setCharacterEncoding("utf-8");
            // 设置浏览器端解码
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(Result.unauthorized()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
