package com.ruoyi.order.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 币币订单对象 rc_currency_order
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
public class RcCurrencyOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 交易单号 */
    @Excel(name = "交易单号")
    private String orderId;

    /** 币币订单发布表id */
    @Excel(name = "币币订单发布表id")
    private Long rcCurrencyOrderReleaseId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 确认付款时间 */
    @Excel(name = "确认付款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmThePaymentTime;

    /** 订单状态1：未完成 2：已完成 */
    @Excel(name = "订单状态1：未完成 2：已完成")
    private String orderState;

    /** 确认收款时间 */
    @Excel(name = "确认收款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmCollectionTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setRcCurrencyOrderReleaseId(Long rcCurrencyOrderReleaseId) 
    {
        this.rcCurrencyOrderReleaseId = rcCurrencyOrderReleaseId;
    }

    public Long getRcCurrencyOrderReleaseId() 
    {
        return rcCurrencyOrderReleaseId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setConfirmThePaymentTime(Date confirmThePaymentTime) 
    {
        this.confirmThePaymentTime = confirmThePaymentTime;
    }

    public Date getConfirmThePaymentTime() 
    {
        return confirmThePaymentTime;
    }
    public void setOrderState(String orderState) 
    {
        this.orderState = orderState;
    }

    public String getOrderState() 
    {
        return orderState;
    }
    public void setConfirmCollectionTime(Date confirmCollectionTime) 
    {
        this.confirmCollectionTime = confirmCollectionTime;
    }

    public Date getConfirmCollectionTime() 
    {
        return confirmCollectionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("rcCurrencyOrderReleaseId", getRcCurrencyOrderReleaseId())
            .append("userId", getUserId())
            .append("confirmThePaymentTime", getConfirmThePaymentTime())
            .append("orderState", getOrderState())
            .append("createTime", getCreateTime())
            .append("confirmCollectionTime", getConfirmCollectionTime())
            .toString();
    }
}
