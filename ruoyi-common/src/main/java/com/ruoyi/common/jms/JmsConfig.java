package com.ruoyi.common.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.Session;

@EnableJms
@Configuration
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String url;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(username, password, url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public Queue queue(){
        return new ActiveMQQueue();
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory, Queue queue){
        JmsTemplate jmsTemplate=new JmsTemplate();
        //定义持久化后节点挂掉以后，重启可以继续消费.NON_PERSISTENT 表示非持久化，PERSISTENT 表示持久化
        jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列
//        Session.AUTO_ACKNOWLEDGE  消息自动签收
//        Session.CLIENT_ACKNOWLEDGE  客户端调用acknowledge方法手动签收
//        Session.DUPS_OK_ACKNOWLEDGE 不必必须签收，消息可能会重复发送
        jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);//客户端签收模式
        return jmsTemplate;
    }

    /**
     * 定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public DefaultJmsListenerContainerFactory queueListenerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        return factory;
    }

    /**
     * 定义一个消息监听器连接工厂，这里定义的是发布/订阅模式的监听器连接工厂
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public DefaultJmsListenerContainerFactory topicListenerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

}
