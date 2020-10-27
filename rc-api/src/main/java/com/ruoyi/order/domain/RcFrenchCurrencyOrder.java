package com.ruoyi.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 法币订单对象 rc_french_currency_order
 * 
 * @author ruoyi
 * @date 2020-10-27
 */
public class RcFrenchCurrencyOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 交易单号 */
    @Excel(name = "交易单号")
    private String orderId;

    /** 法币订单发布表id */
    @Excel(name = "法币订单发布表id")
    private Long rcFrenchCurrencyOrderReleaseId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 付款截图 */
    @Excel(name = "付款截图")
    private String paymentImg;

    /** 订单状态1：等待确认收款2：超时未确认收款3：等待确认付款4：超时未确认付款5：已确认6：申诉中7：申诉成功8：申诉失败 */
    @Excel(name = "订单状态1：等待确认收款2：超时未确认收款3：等待确认付款4：超时未确认付款5：已确认6：申诉中7：申诉成功8：申诉失败")
    private String orderState;

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
    public void setRcFrenchCurrencyOrderReleaseId(Long rcFrenchCurrencyOrderReleaseId) 
    {
        this.rcFrenchCurrencyOrderReleaseId = rcFrenchCurrencyOrderReleaseId;
    }

    public Long getRcFrenchCurrencyOrderReleaseId() 
    {
        return rcFrenchCurrencyOrderReleaseId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPaymentImg(String paymentImg) 
    {
        this.paymentImg = paymentImg;
    }

    public String getPaymentImg() 
    {
        return paymentImg;
    }
    public void setOrderState(String orderState) 
    {
        this.orderState = orderState;
    }

    public String getOrderState() 
    {
        return orderState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("rcFrenchCurrencyOrderReleaseId", getRcFrenchCurrencyOrderReleaseId())
            .append("userId", getUserId())
            .append("paymentImg", getPaymentImg())
            .append("orderState", getOrderState())
            .append("createTime", getCreateTime())
            .toString();
    }
}
