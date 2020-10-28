package com.ruoyi.common.jms;

public class JmsConstant {

    // 消息队列名命名规则：模块名+队列类型（queue/topic）+方法名

    /**
     * testMq
     */
    public static final String queueTestMq = "mq.queue.testMq";

    /**
     * 用户资金统计
     */
    public static String queueMoneyUserStatistics = "money.queue.moneyUserStatistics";
}
