package com.ruoyi;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.jms.Message;
import com.ruoyi.common.jms.ReceiverService;
import com.ruoyi.framework.web.domain.server.Sys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TestMQService implements ReceiverService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    @JmsListener(destination = "mq.queue.testMq", containerFactory = "queueListenerFactory")
    public void receiveQueue(String message) throws InterruptedException {
        String lockKey = "lockKey:moneyUserStatistics:" + 1;
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待l秒，上锁以后l1秒自动解锁
            if (fairLock.tryLock(50, 100, TimeUnit.SECONDS)) {
                JSONObject data = Message.getMessageData(message);
                System.out.println(new Date());
                System.out.println(data);
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        } finally {
            if (fairLock.isHeldByCurrentThread()) {
                fairLock.unlock();
            }
        }
    }
}
