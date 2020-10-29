package com.ruoyi.order.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class UnpaidOvertimeMQService implements ReceiverService {


    @Autowired
    private static final Logger log = LoggerFactory.getLogger(UnpaidOvertimeMQService.class);
    @Autowired
    private RcFrenchCurrencyOrderMapper rcFrenchCurrencyOrderMapper;

    @Autowired
    private LegalCurrencyMapper legalCurrencyMapper;

    @Override
    @JmsListener(destination = JmsConstant.queueUnpaidOvertime, containerFactory = "queueListenerFactory")
    public void receiveQueue(String message) {
        log.info("法币订单超时未确认付款");
        JSONObject data = Message.getMessageData(message);
        String orderId = data.get("orderId").toString();
        RcFrenchCurrencyOrder rcFrenchCurrencyOrder = legalCurrencyMapper.selectRcFrenchCurrencyOrderByOrderId(orderId);
        if ("3".equals(rcFrenchCurrencyOrder.getOrderState())) {
            rcFrenchCurrencyOrder.setOrderState("4");
            rcFrenchCurrencyOrderMapper.updateRcFrenchCurrencyOrder(rcFrenchCurrencyOrder);
        }
    }
}
