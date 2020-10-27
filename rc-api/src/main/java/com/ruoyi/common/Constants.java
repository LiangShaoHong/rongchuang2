package com.ruoyi.common;

public class Constants {

    /**
     * 客户端有两种方式传递令牌: Header中X-Token, 或Cookie中X-Token
     */
    public static final String X_TOKEN = "X_Token";

    /**
     * 用户登录过期时间（秒）
     */
    public static final long LOGIN_TIMEOUT = 1 * 24 * 60 * 60;

    /**
     * redis User库
     */
    public static final String DB_USER = "user:";

    /**
     * redis Token库
     */
    public static final String DB_TOKEN = "token:";

    /**
     * redis Config库
     */
    public static final String DB_CONFIG = "config:";

    /**
     * redis Token库
     */
    public static final String DB_ORDER = "order:";

    /**
     * 用户对应系统收款账号储存过期时间（秒）
     */
    public static final long SYS_BANK_TIMEOUT = 30 * 60;

    /**
     * 短信验证码过期时间（秒）
     */
    public static final long MESSAGE_VERIFICATIONCODE_TIMEOUT = 10 * 60;

}
