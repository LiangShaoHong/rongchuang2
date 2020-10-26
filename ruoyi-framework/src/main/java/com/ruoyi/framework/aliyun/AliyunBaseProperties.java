package com.ruoyi.framework.aliyun;

import java.io.Serializable;

public class AliyunBaseProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阿里云AccessKeyId
     */
    private String accessKeyId;

    /**
     * 阿里云AccessKeySecret
     */
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
