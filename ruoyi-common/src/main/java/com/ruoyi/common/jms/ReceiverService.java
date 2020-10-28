package com.ruoyi.common.jms;

/**
 * @author xiaoxia
 */
public interface ReceiverService {

    /**
     * 接收队列消息
     * @param message
     */
    public void receiveQueue(String message);

}
