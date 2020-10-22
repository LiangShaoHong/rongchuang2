package com.ruoyi.dxapi.controller;

import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.dxapi.common.Constants;
import com.ruoyi.dxapi.controller.task.TaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    /**
     * 请求来源站点
     *
     * @param request
     * @return
     */
    protected String getOrigin(HttpServletRequest request) {

        String origin = request.getHeader("origin");
        if (StringUtils.isEmpty(origin)) {
            origin = request.getServerName();
        } else {
            origin = origin.replace("http://", "").replace("https://", "");
        }
        // 如果有端口，则要截掉端口
        /*if (origin.indexOf(":") > 0) {
            origin = origin.substring(0, origin.indexOf(":"));
        }*/
        log.info("域名信息---------:{}",origin);
        return origin;
    }


    /**
     * 获取拓展参数
     * @param request
     * @return
     */
    protected Map<String, Object> extractPublicParam(HttpServletRequest request) {

        String token = request.getHeader(Constants.X_TOKEN);

        Map<String, Object> map = new HashMap();
        if (StringUtils.isNotEmpty(token)) {
            Long account_id = Long.valueOf(JWTUtil.getUsername(token));
            map.put("extra_account_id", account_id);
            map.put("extra_" + Constants.X_TOKEN, token);
        }
        map.put("extra_schema", request.getScheme());
        map.put("extra_servername", request.getServerName());
        map.put("extra_port", request.getServerPort());
        map.put("extra_ip", IpUtils.getIpAddr(request));
        map.put("extra_origin", getOrigin(request));
        map.put("extra_session-id", request.getSession().getId());
        map.put("extra_user_agent", request.getHeader("user-agent"));
        map.put("extra_client-type", request.getHeader("client-type"));
        return map;
    }



}
