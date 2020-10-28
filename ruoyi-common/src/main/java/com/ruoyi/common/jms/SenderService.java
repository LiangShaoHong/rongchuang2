package com.ruoyi.common.jms;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.jms.bean.DelayMessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SenderService {


    @Autowired
    private JmsTemplate jmsTemplate;


    /**
     * 发送队列消息
     * @param destination 队列名称
     * @param data 数据
     */
    public void sendQueueMessage(String destination, Object data) {

        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(data));
        jmsTemplate.setPubSubDomain(false);
        jmsTemplate.convertAndSend(destination, JSONObject.toJSONString(Message.message(destination, json)));
    }


    /**
     * 发送订阅消息
     * @param destination 队列名称
     * @param data 数据
     */
    public void sendTopicMessage(String destination, JSONObject data) {

        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.convertAndSend(destination, JSONObject.toJSONString(Message.message(destination, data)));
    }

    /**
     * 发送延迟队列消息
     * @param destination 队列名称
     * @param data 数据
     */
    public void sendQueueDelayMessage(String destination, JSONObject data,int delay) {

        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(data));
        jmsTemplate.setPubSubDomain(false);
        DelayMessagePostProcessor postProcessor = new DelayMessagePostProcessor(delay);
        jmsTemplate.convertAndSend(destination, JSONObject.toJSONString(Message.message(destination, json)), postProcessor);
    }

}
