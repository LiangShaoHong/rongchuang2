package com.ruoyi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.redis.RedisService;

import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class RequestInterceptor implements HandlerInterceptor {


    /**
     * 系统日志
     */
    private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @Autowired
    private RedisService redisService;


    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * return false时，被请求时，拦截器执行到此处将不会继续操作
     * return true时，请求将会继续执行后面的操作
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 设置服务器端编码
        response.setCharacterEncoding("utf-8");
        // 设置浏览器端解码
        response.setContentType("text/html;charset=utf-8");

        // 获取请求路径
        String uri = request.getRequestURI();

        try {

            // ip验证
//            Result ipCount = checkIPCount(request, response);
//            if (!ipCount.isStatus()) {
//                response.getWriter().write(JSONObject.toJSONString(ipCount));
//                return false;
//            }

            // 短信请求验证
            if (uri.indexOf("getVerificationCode") > 0) {
                Result msgCount = checkTelMsgCount(request, response);
                if (!msgCount.isStatus()) {
                    response.getWriter().write(JSONObject.toJSONString(msgCount));
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 验证ip请求次数
     * @param request
     * @param response
     * @return
     */
    private Result checkIPCount(HttpServletRequest request, HttpServletResponse response){

        long maxCount = 100;
        String ip = IpUtils.getIpAddr(request);
        String ipKey = Constants.DB_CONFIG + "ip:" + ip;
        long ipCount = redisService.incr(ipKey, 60, TimeUnit.SECONDS);
        log.info(ip+":访问次数--"+ipCount);
//        if (ipCount > maxCount) {
//            return Result.isFail("请求过于频繁，请休息片刻再来！");
//        }
        return Result.isOk();
    }


    /**
     * 验证短信请求次数
     * @param request
     * @param response
     * @return
     */
    private Result checkTelMsgCount(HttpServletRequest request, HttpServletResponse response){

        long maxCount = 1;
        String tel = request.getParameter("tel");
        String msgKey = Constants.DB_CONFIG + "msg:" + tel;
        long msgCount = redisService.incr(msgKey, 60, TimeUnit.SECONDS);
        log.info(tel+":访问次数--"+msgCount);
        if (msgCount > maxCount) {
            return Result.isFail("请求过于频繁，请休息片刻再来！");
        }
        return Result.isOk();
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
