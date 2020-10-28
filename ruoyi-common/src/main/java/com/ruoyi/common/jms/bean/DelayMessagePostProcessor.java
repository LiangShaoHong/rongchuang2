package com.ruoyi.common.jms.bean;

import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.MessagePostProcessor;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * 延时消息数据对象
 *
 * @author cjunyuan
 */
public class DelayMessagePostProcessor implements MessagePostProcessor {

    private long delay = 0L;

    private String corn = null;

    public DelayMessagePostProcessor(long delay) {
        this.delay = delay;
    }

    public DelayMessagePostProcessor(String cron) {
        this.corn = cron;
    }

    @Override
    public Message postProcessMessage(Message message) throws JMSException {
        if (delay > 0) {
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        }
        if (null != corn && !"".equals(corn)) {
            message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, corn);
        }
        return message;
    }
}
