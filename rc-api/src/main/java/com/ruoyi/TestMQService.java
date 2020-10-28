package com.ruoyi;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TestMQService implements ReceiverService {

    @Override
    @JmsListener(destination = "mq.queue.testMq", containerFactory = "queueListenerFactory")
    public void receiveQueue(String message) {
        JSONObject data = Message.getMessageData(message);
        System.out.println(data);
    }
}
