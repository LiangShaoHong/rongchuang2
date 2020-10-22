package com.ruoyi.dxapi.interceptor;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.dx.domain.PlatformBase;
import com.ruoyi.dx.dto.PlatformSysConfDTO;
import com.ruoyi.dx.service.IPlatformBaseService;
import com.ruoyi.dxservice.service.SysPlatformInfoService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cjunyuan
 */
@Component
public class AdminRequestInterceptor implements HandlerInterceptor {


    /**
     * 系统日志
     */
    private static final Logger log = LoggerFactory.getLogger(AdminRequestInterceptor.class);
    @Autowired
    private SysPlatformInfoService sysPlatformInfoService;
    @Autowired
    private IPlatformBaseService platformBaseService;


    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * return false时，被请求时，拦截器执行到此处将不会继续操作
     * return true时，请求将会继续执行后面的操作
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 设置服务器端编码
        response.setCharacterEncoding("utf-8");
        // 设置浏览器端解码
        response.setContentType("text/html;charset=utf-8");

        try {
            String ipAddr = IpUtils.getIpAddr(request);

            SysUser currentUser = ShiroUtils.getSysUser();
            String platformId = "0";
            if (null != currentUser && null != currentUser.getDeptId()) {
                //如果是平台的管理员，返回该平台的id
                if (!currentUser.isAdmin()) {
                    PlatformBase platformBase = platformBaseService.getPlatformBaseByDeptId(currentUser.getDeptId());
                    if (null == platformBase){
                        response.getWriter().append("<h1 style=\"text-align:center;\">Platform is not init!</h1>");
                        return false;
                    }
                    platformId = platformBase.getId();
                }
            }
            JSONObject sysConfObject = sysPlatformInfoService.getSysConf(platformId, InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
            PlatformSysConfDTO sysConf = JSON.parseObject(sysConfObject.toString(), PlatformSysConfDTO.class);
            if (null == sysConf.getOpenWhiteIp() || MsgConstants.IS_NO == sysConf.getOpenWhiteIp()){
                return true;
            }
            if (null != sysConf && null != sysConf.getWhiteIp() && !"".equals(sysConf.getWhiteIp())){
                if (sysConf.getWhiteIp().indexOf(ipAddr) == -1){
                    response.getWriter().append("<h1 style=\"text-align:center;\">Not allowed!</h1>");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
