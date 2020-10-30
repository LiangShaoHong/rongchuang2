package com.ruoyi;

import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.digital.service.IRcDigitalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 * @author xiaoxia
 */
@Api("首页公告 轮播 系统消息 帮助接口")
@RestController
@RequestMapping("/rc-api/mq")
public class TestMQController {

    @Autowired
    private SenderService senderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IRcDigitalService rcService;

    @PostMapping("/testMQ")
    public Result testMQ() {
//        JSONObject data = new JSONObject();
//        data.put("name", "张三");
//        senderService.sendQueueMessage(JmsConstant.queueTestMq, data);
//        senderService.sendQueueDelayMessage(JmsConstant.queueTestMq, data, 10000);

        JSONObject data = new JSONObject();
        data.put("rcService", rcService.getDataList(0,100));
        redisService.set("GG", data, Constants.DB_USER);

        JSONObject gg = (JSONObject) redisService.get("GG", Constants.DB_USER);
        return Result.isOk().data(gg.get("rcService"));

    }
}
