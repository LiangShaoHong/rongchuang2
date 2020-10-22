package com.ruoyi.dxapi.controller.common;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.dx.domain.PlatformBase;
import com.ruoyi.dx.service.IPlatformBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * url统一处理
 */
@Controller("dxUrlController")
public class URLController {

    /**
     * 系统日志
     */
    private static final Logger log = LoggerFactory.getLogger(URLController.class);


    @Autowired
    private IPlatformBaseService platformBaseService;


    /**
     * 判断是否是微信访问
     *
     * @param request
     * @return
     */
    public static boolean isWeChat(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        return userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;
    }


    /**
     * 分享链接处理
     *
     * @return
     */
    @RequestMapping("/share/{platformId}/{code}")
    public String share(@PathVariable String platformId, @PathVariable String code, HttpServletRequest request) {

        if (isWeChat(request)) {

            return "wx/wx_share";

        } else {

            PlatformBase platformBase = platformBaseService.selectPlatformBaseById(platformId);
            if (platformBase != null && StringUtils.isNotEmpty(platformBase.getDomain())) {
                String url = "http://" + platformBase.getDomain() + "/#/pages/account/register?code=" + code;
                return "redirect:" + url;
            }

            return "wx/404";
        }
    }

}
