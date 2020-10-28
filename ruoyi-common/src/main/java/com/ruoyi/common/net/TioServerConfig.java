package com.ruoyi.common.net;

/**
 * TIO 配置文件
 */
public abstract class TioServerConfig {

    /**
     * 协议名字
     */
    public static final String PROTOCOL_NAME = "wx";

    /**
     * 编码
     */
    public static final String CHARSET = "utf-8";

    /**
     * 监听端口
     */
    public static final int SERVER_PORT = 9326;

    /**
     * 心跳超时时间，单位：毫秒
     */
    public static final int HEARTBEAT_TIMEOUT = 1000 * 60;

    /**
     * 心跳
     */
    public static final String MSG_PING = "0";

}
