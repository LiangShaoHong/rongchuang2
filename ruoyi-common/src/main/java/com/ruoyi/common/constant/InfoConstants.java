package com.ruoyi.common.constant;

/**
 * 平台系统设置表key常量
 */
public class InfoConstants {

    /**
     * 系统信息
     */
    public static final String PLATFORM_SYS_INFO = "sysInfo";

    /**
     * 系统设置
     */
    public static final String PLATFORM_SYS_CONF = "sysConf";

    /**
     * 用户等级升级设置
     */
    public static final String RANK_CONF = "rankConf";

    /**
     * 任物设置与任务分销
     */
    public static final String TASK_CONF = "taskConf";

    /**
     * 提现设置
     */
    public static final String CASH_CONF = "cashConf";

    /**
     * 转账设置
     */
    public static final String TRASFER_CONF = "transferConf";

    /**
     * 支付设置
     */
    public static final String PAY_CONF = "payConf";

    /**
     * 平台银行卡信息
     */
    public static final String BANK_CONF="bankConf";
    /**
     * 充值设置
     */
    public static final String RECHARGE_CONF = "rechargeConf";

    /**
     * 短信设置
     */
    public static final String MESSAGE_CONF = "messageConf";
    /**
     * 下载设置
     */
    public static final String DOWNLOAD_CONF = "downloadConf";
    /**
     * 短信设置
     */
    public static final String SMS_CONF = "smsConf";

    /**
     * redis system库
     */
    public static final String REDIS_SYSTEM = "system";

    /**
     * redis 充值提示信息key
     */
    public static final String REDIS_RECHARGE_CONF = "rechargeConf:";
    /**
     * redis 提现提示信息key
     */
    public static final String REDIS_CASH_CONF = "cashConf:";
    /**
     * redis 转账提示信息key
     */
    public static final String REDIS_TRASFER_CONF = "transferConf:";

    /**
     *  redis 系统信息key
     */
    public static final String REDIS_PLATFORM_SYS_CONF = "sysConf:";

    /**
     * redis 用户等级升级设置key
     */
    public static final String REDIS_RANK_CONF = "rankConf:";

    /**
     *  redis下载设置key
     */
    public static final String REDIS_DOWNLOAD_CONF = "downloadConf:";

    /**
     * redis 分销提示信息
     */
    public static final String REDIS_TASK_CONF = "taskConf:";

    /**
     * redis 转账提示信息
     */
    public static final String REDIS_PAY_CONF = "payConf:";

    /**
     * redis 短信设置key
     */
    public static final String REDIS_SMS_CONF = "smsConf:";

    /**
     * redis 平台db
     */
    public static final String DB_PLATE = "platform:";

    /**
     * redis 存平台公告集合(公告key= DB_PLATE:article:平台id )
     */
    public static final String ARTICLE = "article:";

    /**
     * 手机号码
     */
    public static final String VERIFICATION_PHONE_MAP = "verification_phone_map:";

    /**
     * redis 短信db
     */
    public static final String DB_MESSAGE = "message:";


    /*
    *用户端redis消息list
    *      :任务消息20条 ==> 用户账号
    *      :提现消息10条 ==> {"account":"314****34","money":"34.00"}
    */

    /**
     * redis 滚动消息
     */
    public static final String DB_NOTICE = "notice:";
    /**
     * redis User库
     */
    public static final String DB_USER = "user:";
    /**
     * 任务完成列表
     */
    public static final String TASK_LIST = "task_list";
    /**
     * 提现消息列表
     */
    public static final String CASH_LIST = "cash_list";

    public static final Long CASH_LENGTH = Long.valueOf(5);

    public static final Long TASK_LENGTH = Long.valueOf(20);

    /**
     *  redis otc系统配置信息key
     */
    public static final String REDIS_OTC_SYS_CONF = "otcSysConf:";

    /**
     * 领取任务间隔
     */
    public static final String GET_TASK_COMM = "task:get:comm:";
    public static final String GET_TASK_FIRE = "task:get:fire:";
    public static final Long GET_TASK_TIME = (long)5;

}
