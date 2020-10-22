package com.ruoyi.dxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.service.IUserAgentCommissionService;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * test
 */
@RestController("dxTestController")
public class TestController {

    /**
     * 系统日志
     */
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IUserAgentCommissionService userAgentService;

    @Autowired
    private UserService userService;


    @RequestMapping("/dx-api/base/testCommission")
    public Result testCommission(HttpServletRequest request) {

//        String platformId = request.getParameter("platformId");
//        String userId = request.getParameter("userId");
//        User user = userService.getUserById(userId);
//        boolean result = userAgentService.executeCommission(platformId, MsgConstants.AgentCommissionType.TASK, user, BigDecimal.valueOf(100), 3);
//        if (result) {
//            return Result.isOk();
//        }
        return Result.isFail();
    }

    @Autowired
    private SenderService senderService;


    /**
     * testMQ
     *
     * @return
     */
    @RequestMapping("/dx-api/base/testMQ")
    public Result testMQ() {

        JSONObject data = new JSONObject();
        data.put("name", "张三");
        senderService.sendQueueMessage(JmsConstant.queueTestMq, data);

        return Result.isOk();
    }

}
