package com.ruoyi.order.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import com.ruoyi.order.domain.RcCurrencyOrder;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.mapper.CurrencyMapper;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.mapper.RcCurrencyOrderMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class BbOvertimeMQService implements ReceiverService {


    @Autowired
    private static final Logger log = LoggerFactory.getLogger(BbOvertimeMQService.class);
    @Autowired
    private RcCurrencyOrderMapper rcCurrencyOrderMapper;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    @JmsListener(destination = JmsConstant.queueBbOvertime, containerFactory = "queueListenerFactory")
    public void receiveQueue(String message) {
        log.info("币币订单自动确认");
        JSONObject data = Message.getMessageData(message);
        String orderId = data.get("orderId").toString();
        RcCurrencyOrder rcCurrencyOrder = currencyMapper.selectRcCurrencyOrderByOrderId(orderId);
        rcCurrencyOrder.setOrderState("5");
        rcCurrencyOrder.setConfirmThePaymentTime(new Date());
        rcCurrencyOrder.setConfirmCollectionTime(new Date());
        rcCurrencyOrderMapper.updateRcCurrencyOrder(rcCurrencyOrder);
    }
}
