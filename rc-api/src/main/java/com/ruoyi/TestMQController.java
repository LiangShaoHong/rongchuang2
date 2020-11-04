package com.ruoyi;

import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.digital.domain.RcTransactionDataDigital;
import com.ruoyi.digital.mapper.RcDigitalMapper;
import com.ruoyi.digital.service.IRcDigitalService;
import com.ruoyi.user.service.IUserMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IUserMoneyService userMoneyService;

    @Autowired
    private RcDigitalMapper rcDigitalMapper;

    @PostMapping("/testMQ")
    public Result testMQ(HttpServletRequest request) {
//        JSONObject data = new JSONObject();
//        data.put("name", "张三");
//        senderService.sendQueueMessage(JmsConstant.queueTestMq, data);
//        senderService.sendQueueDelayMessage(JmsConstant.queueTestMq, data, 10000);

        // websocket 通知后台 有用户登陆 首页页面输出用户名和音乐
//        JSONObject msg = new JSONObject();
//        msg.put("code", "1");
//        msg.put("account", user.getAccount());
//        msg.put("data", "withdraw");
//        pushService.sendToGroup(user.getPlatformId(), msg.toString());
//
//        JSONObject msg1 = new JSONObject();
//        msg1.put("xx", "我来了");
//        pushService.sendToUser("2", msg1.toString());

//        JSONObject data = new JSONObject();
//        data.put("rcService", rcService.getDataList(0,100, 1 ,1));
//        redisService.set("GG", data, "GG:");
//
//        JSONObject gg = (JSONObject) redisService.get("GG", "GG:");
//        return Result.isOk().data(gg.get("rcService"));

        int pageNum = request.getParameter("pageNum") == null ? 1 : Integer.parseInt(request.getParameter("pageNum"));
        int pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request.getParameter("pageSize"));

//        List<RcTransactionDataDigital> list = rcDigitalMapper.getDataList(1,100);
//        JSONObject data = new JSONObject();
//        data.put("rcService", list);
//        redisService.set("rcService", rcDigitalMapper.getDataList(1,100), "GG:");
//        redisService.rPush("rxxx", rcDigitalMapper.getDataList(1,100), "GG:");

//        List<RcTransactionDataDigital> newList = list.stream()
//                .sorted(Comparator.comparing(RcTransactionDataDigital::getMarketValueUsd))
//                .collect(Collectors.toList());
//
//        List<RcTransactionDataDigital> newListTwo = list.stream()
//                .sorted(Comparator.comparing(RcTransactionDataDigital::getMarketValueUsd)
//                        .reversed())
//                .collect(Collectors.toList());
//        List<RcTransactionDataDigital> zz = (List<RcTransactionDataDigital>) redisService.get("rcService","GG:");
//        System.out.println(zz);
        JSONObject msg = new JSONObject();
        msg.put("list", redisService.lRange("rxxx", "GG:", pageSize * (pageNum - 1), (pageSize * pageNum ) - 1));
//        msg.put("list", list);
//        msg.put("newList", newList);
//        msg.put("newListTwo", newListTwo);
        return Result.isOk().data(msg);
    }

    @ApiOperation("加减币测试接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "userId", value = "交易会员id", required = true),
                    @ApiImplicitParam(dataType = "String", name = "userName", value = "交易会员名称", required = true),
                    @ApiImplicitParam(dataType = "String", name = "fromUserId", value = "交易对象id(订单ID)", required = true),
                    @ApiImplicitParam(dataType = "String", name = "money", value = "金额变化值", required = true),
                    @ApiImplicitParam(dataType = "String", name = "cashHandFee", value = "手续费(平台佣金)", required = true),
                    @ApiImplicitParam(dataType = "String", name = "recordType", value = "资金变化类型 0转账 1提现 2充值 3后台人员操作", required = true),
                    @ApiImplicitParam(dataType = "String", name = "mark", value = "备注说明", required = true)
            })
    @PostMapping("/updateMoney")
    public Result userMoneyService(
            @RequestParam("userId") String userId,
            @RequestParam("userName") String userName,
            @RequestParam("fromUserId") String fromUserId,
            @RequestParam("money") String money,
            @RequestParam("cashHandFee") String cashHandFee,
            @RequestParam("recordType") String recordType,
            @RequestParam("mark") String mark,
            HttpServletRequest request) {
        boolean isSame = userMoneyService.moneyDoCenter(userId,userName,fromUserId,new BigDecimal(money),new BigDecimal(cashHandFee),recordType,mark);
        return Result.isOk().data(isSame);
    }
}
