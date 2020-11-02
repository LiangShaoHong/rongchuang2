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

    /** 二维码 */
    @Excel(name = "二维码")
    private String qrcode;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCollectionInformation(String collectionInformation) 
    {
        this.collectionInformation = collectionInformation;
    }

    public String getCollectionInformation() 
    {
        return collectionInformation;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setQrcode(String qrcode) 
    {
        this.qrcode = qrcode;
    }

    public String getQrcode() 
    {
        return qrcode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("collectionInformation", getCollectionInformation())
            .append("region", getRegion())
            .append("qrcode", getQrcode())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
