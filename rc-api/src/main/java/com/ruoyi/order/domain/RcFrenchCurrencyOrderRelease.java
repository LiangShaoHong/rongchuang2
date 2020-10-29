package com.ruoyi.order.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 法币订单发布对象 rc_french_currency_order_release
 * 
 * @author ruoyi
 * @date 2020-10-22
 */
public class RcFrenchCurrencyOrderRelease extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 交易类型 */
    @Excel(name = "交易类型")
    private String transactionType;

    /** 订单类型 1：后台发布 2：用户提现 3：第三方需求 4：自动分配订单 5：指派订单 */
    @Excel(name = "订单类型 1：后台发布 2：用户提现 3：第三方需求 4：自动分配订单 5：指派订单")
    private String orderType;

    /** 卖家id */
    @Excel(name = "卖家id")
    private Long sellerUserId;

    /** 买入来源 */
    @Excel(name = "买入来源")
    private String buyingSources;

    /** 卖出去向 */
    @Excel(name = "卖出去向")
    private String whereToSell;

    /** 数量 */
    @Excel(name = "数量")
    private Long number;

    /** 交易币种 */
    @Excel(name = "交易币种")
    private String transactionCurrency;

    /** 买入数量 */
    @Excel(name = "买入数量")
    private Long purchaseQuantity;

    /** 买入价格 */
    @Excel(name = "买入价格")
    private Long purchasePrice;

    /** 出售数量 */
    @Excel(name = "出售数量")
    private Long sellQuantity;

    /** 出售价格 */
    @Excel(name = "出售价格")
    private Long sellPrice;

    /** 花费USDT */
    @Excel(name = "花费USDT")
    private Long spendUsdt;

    /** 可得法币 */
    @Excel(name = "可得法币")
    private Long availableFiatMoney;

    /** 可收益 */
    @Excel(name = "可收益")
    private Long profit;

    /** 订单状态 1：已分配 2：未分配 */
    @Excel(name = "订单状态 1：已分配 2：未分配")
    private String orderState;

    /** 指派用户id */
    @Excel(name = "指派用户id")
    private Long assignUserId;

    /** 订单所属区域 */
    @Excel(name = "订单所属区域")
    private String area;

    /** 订单发布时间 */
    @Excel(name = "订单发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTransactionType(String transactionType) 
    {
        this.transactionType = transactionType;
    }

    public String getTransactionType() 
    {
        return transactionType;
    }
    public void setOrderType(String orderType) 
    {
        this.orderType = orderType;
    }

    public String getOrderType() 
    {
        return orderType;
    }
    public void setSellerUserId(Long sellerUserId) 
    {
        this.sellerUserId = sellerUserId;
    }

    public Long getSellerUserId() 
    {
        return sellerUserId;
    }
    public void setBuyingSources(String buyingSources) 
    {
        this.buyingSources = buyingSources;
    }

    public String getBuyingSources() 
    {
        return buyingSources;
    }
    public void setWhereToSell(String whereToSell) 
    {
        this.whereToSell = whereToSell;
    }

    public String getWhereToSell() 
    {
        return whereToSell;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setTransactionCurrency(String transactionCurrency) 
    {
        this.transactionCurrency = transactionCurrency;
    }

    public String getTransactionCurrency() 
    {
        return transactionCurrency;
    }
    public void setPurchaseQuantity(Long purchaseQuantity) 
    {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() 
    {
        return purchaseQuantity;
    }
    public void setPurchasePrice(Long purchasePrice) 
    {
        this.purchasePrice = purchasePrice;
    }

    public Long getPurchasePrice() 
    {
        return purchasePrice;
    }
    public void setSellQuantity(Long sellQuantity) 
    {
        this.sellQuantity = sellQuantity;
    }

    public Long getSellQuantity() 
    {
        return sellQuantity;
    }
    public void setSellPrice(Long sellPrice) 
    {
        this.sellPrice = sellPrice;
    }

    public Long getSellPrice() 
    {
        return sellPrice;
    }
    public void setSpendUsdt(Long spendUsdt) 
    {
        this.spendUsdt = spendUsdt;
    }

    public Long getSpendUsdt() 
    {
        return spendUsdt;
    }
    public void setAvailableFiatMoney(Long availableFiatMoney) 
    {
        this.availableFiatMoney = availableFiatMoney;
    }

    public Long getAvailableFiatMoney() 
    {
        return availableFiatMoney;
    }
    public void setProfit(Long profit) 
    {
        this.profit = profit;
    }

    public Long getProfit() 
    {
        return profit;
    }
    public void setOrderState(String orderState) 
    {
        this.orderState = orderState;
    }

    public String getOrderState() 
    {
        return orderState;
    }
    public void setAssignUserId(Long assignUserId) 
    {
        this.assignUserId = assignUserId;
    }

    public Long getAssignUserId() 
    {
        return assignUserId;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setReleaseTime(Date releaseTime) 
    {
        this.releaseTime = releaseTime;
    }

    public Date getReleaseTime() 
    {
        return releaseTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("transactionType", getTransactionType())
            .append("orderType", getOrderType())
            .append("sellerUserId", getSellerUserId())
            .append("buyingSources", getBuyingSources())
            .append("whereToSell", getWhereToSell())
            .append("number", getNumber())
            .append("transactionCurrency", getTransactionCurrency())
            .append("purchaseQuantity", getPurchaseQuantity())
            .append("purchasePrice", getPurchasePrice())
            .append("sellQuantity", getSellQuantity())
            .append("sellPrice", getSellPrice())
            .append("spendUsdt", getSpendUsdt())
            .append("availableFiatMoney", getAvailableFiatMoney())
            .append("profit", getProfit())
            .append("orderState", getOrderState())
            .append("assignUserId", getAssignUserId())
            .append("area", getArea())
            .append("releaseTime", getReleaseTime())
            .toString();
    }
}
