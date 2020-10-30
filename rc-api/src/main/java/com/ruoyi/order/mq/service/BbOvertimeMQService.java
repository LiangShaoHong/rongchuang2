package com.ruoyi.order.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.domain.RcCurrencyOrder;
import com.ruoyi.order.domain.RcCurrencyOrderRelease;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.mapper.*;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.service.IUserMoneyService;
import com.ruoyi.user.service.IUserProfitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;


@Component
public class BbOvertimeMQService implements ReceiverService {


    @Autowired
    private static final Logger log = LoggerFactory.getLogger(BbOvertimeMQService.class);
    @Autowired
    private RcCurrencyOrderMapper rcCurrencyOrderMapper;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private IUserMoneyService iUserMoneyService;

    @Autowired
    private RcCurrencyOrderReleaseMapper rcCurrencyOrderReleaseMapper;

    @Autowired
    private IUserProfitService iUserProfitService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @JmsListener(destination = JmsConstant.queueBbOvertime, containerFactory = "queueListenerFactory")
    public void receiveQueue(String message) {
        log.info("币币订单自动确认");
        JSONObject data = Message.getMessageData(message);
        RcUser user = data.getObject("user",RcUser.class);
        //修改订单状态
        String orderId = data.get("orderId").toString();
        RcCurrencyOrder rcCurrencyOrder = currencyMapper.selectRcCurrencyOrderByOrderId(orderId);
        rcCurrencyOrder.setOrderState("5");
        rcCurrencyOrder.setConfirmThePaymentTime(new Date());
        rcCurrencyOrder.setConfirmCollectionTime(new Date());
        rcCurrencyOrderMapper.updateRcCurrencyOrder(rcCurrencyOrder);

        //修改用户余额
        RcCurrencyOrderRelease rcCurrencyOrderRelease = rcCurrencyOrderReleaseMapper.selectRcCurrencyOrderReleaseById(rcCurrencyOrder.getRcCurrencyOrderReleaseId());
        Long profitnNum = rcCurrencyOrderRelease.getAvailableFiatMoney() - rcCurrencyOrderRelease.getSpendUsdt();
        iUserMoneyService.moneyDoCenter(String.valueOf(user.getId()), user.getAccount(), orderId, new BigDecimal(profitnNum), new BigDecimal(0), "5", "币币交易");

        //修改用户收益
        Profit profit = new Profit();
        profit.setUserId(user.getId().intValue());
        profit.setProfitType("2");
        profit.setEarned(new BigDecimal(profitnNum));
        Integer x = iUserProfitService.update(profit);
        if (x <= 0) {
            iUserProfitService.insert(profit);
        }
    }
}
