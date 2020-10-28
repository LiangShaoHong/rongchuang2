package com.ruoyi.common.jms;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

public class Message implements Serializable{

    private static final long serialVersionUID = -658250125732806493L;

    /**
     * 队列消息名称
     */
    private String destination;

    /**
     * 发送的数据
     */
    private JSONObject data;


    /**
     *
     * 构造消息体
     *
     * @param destination 队列消息名称
     * @param data 发送的数据
     * @return
     */
    public static Message message(String destination, JSONObject data){

        Message message = new Message();
        message.setDestination(destination);
        message.setData(data);
        return message;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }


    /**
     * 获取消息数据
     *
     * @param message
     * @return
     */
    public static JSONObject getMessageData(String message){

        if (StringUtils.isEmpty(message)) {
            return null;
        }
        Message msg = JSONObject.parseObject(message, Message.class);
        return msg.getData();
    }

}
