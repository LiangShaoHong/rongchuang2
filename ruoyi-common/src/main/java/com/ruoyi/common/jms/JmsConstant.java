
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


    /**
     * 法币未付款超时时间
     */
    public static final String queueFbUnpaidOvertime = "mq.queue.legalCurrency.unpaidOvertime";

    /**
     * 法币未收款超时时间
     */
    public static final String queueFbUncollectedOvertime = "mq.queue.legalCurrency.uncollectedOvertime";

    /**
     * 币币自动确认延迟时间
     */
    public static final String queueBbOvertime = "mq.queue.Currency.Overtime";

}
