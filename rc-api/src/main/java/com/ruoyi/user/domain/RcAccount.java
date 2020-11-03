package com.ruoyi.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账户收款对象 rc_account
 * 
 * @author ruoyi
 * @date 2020-11-02
 */
public class RcAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 收款信息 */
    @Excel(name = "收款信息")
    private String collectionInformation;

    /** 区域 */
    @Excel(name = "区域")
    private String region;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 图标 */
    @Excel(name = "字典类型")
    private String dictType;

    /** 二维码 */
    @Excel(name = "二维码")
    private String qrcode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCollectionInformation() {
        return collectionInformation;
    }

    public void setCollectionInformation(String collectionInformation) {
        this.collectionInformation = collectionInformation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "RcAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", collectionInformation='" + collectionInformation + '\'' +
                ", region='" + region + '\'' +
                ", icon='" + icon + '\'' +
                ", dictType='" + dictType + '\'' +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}
