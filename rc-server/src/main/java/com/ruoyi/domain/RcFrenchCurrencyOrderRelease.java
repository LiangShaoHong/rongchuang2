package com.ruoyi.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 法币订单发布对象 rc_french_currency_order_release
 * 
 * @author ruoyi
 * @date 2020-10-19
 */
public class RcFrenchCurrencyOrderRelease extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Long id;

    /** 发布者id */
    @Excel(name = "发布者id")
    private Long sellerUserId;

    /** 交易者id */
    @Excel(name = "交易者id")
    private Long transactionUserId;

    /** 交易类型 1-买 2-卖 */
    @Excel(name = "交易类型 1-买 2-卖")
    private Integer transactionsType;

    /** 交易名称 例：人民币/USDT */
    @Excel(name = "交易名称 例：人民币/USDT")
    private String transactionName;

    /** 发布的数量 */
    @Excel(name = "发布的数量")
    private Long number;

    /** 买入价格 */
    @Excel(name = "买入价格")
    private Long purchasePrice;

    /** 出售价格 */
    @Excel(name = "出售价格")
    private Long sellingPrice;

    /** 可收益 */
    @Excel(name = "可收益")
    private Long profitable;

    /** 发布时间 */
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    /** 交易时间 */
    @Excel(name = "交易时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transactionsTime;

    /** 状态：1-未分配 2-待付款 3-待收款 4-申诉中 5-交易中 6已完成 */
    @Excel(name = "状态：1-未分配 2-待付款 3-待收款 4-申诉中 5-交易中 6已完成")
    private Long state;

    /** 付款截图 */
    @Excel(name = "付款截图")
    private String paymentScreenshot;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSellerUserId(Long sellerUserId) 
    {
        this.sellerUserId = sellerUserId;
    }

    public Long getSellerUserId() 
    {
        return sellerUserId;
    }
    public void setTransactionUserId(Long transactionUserId) 
    {
        this.transactionUserId = transactionUserId;
    }

    public Long getTransactionUserId() 
    {
        return transactionUserId;
    }
    public void setTransactionsType(Integer transactionsType) 
    {
        this.transactionsType = transactionsType;
    }

    public Integer getTransactionsType() 
    {
        return transactionsType;
    }
    public void setTransactionName(String transactionName) 
    {
        this.transactionName = transactionName;
    }

    public String getTransactionName() 
    {
        return transactionName;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setPurchasePrice(Long purchasePrice) 
    {
        this.purchasePrice = purchasePrice;
    }

    public Long getPurchasePrice() 
    {
        return purchasePrice;
    }
    public void setSellingPrice(Long sellingPrice) 
    {
        this.sellingPrice = sellingPrice;
    }

    public Long getSellingPrice() 
    {
        return sellingPrice;
    }
    public void setProfitable(Long profitable) 
    {
        this.profitable = profitable;
    }

    public Long getProfitable() 
    {
        return profitable;
    }
    public void setReleaseTime(Date releaseTime) 
    {
        this.releaseTime = releaseTime;
    }

    public Date getReleaseTime() 
    {
        return releaseTime;
    }
    public void setTransactionsTime(Date transactionsTime) 
    {
        this.transactionsTime = transactionsTime;
    }

    public Date getTransactionsTime() 
    {
        return transactionsTime;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }
    public void setPaymentScreenshot(String paymentScreenshot) 
    {
        this.paymentScreenshot = paymentScreenshot;
    }

    public String getPaymentScreenshot() 
    {
        return paymentScreenshot;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sellerUserId", getSellerUserId())
            .append("transactionUserId", getTransactionUserId())
            .append("transactionsType", getTransactionsType())
            .append("transactionName", getTransactionName())
            .append("number", getNumber())
            .append("purchasePrice", getPurchasePrice())
            .append("sellingPrice", getSellingPrice())
            .append("profitable", getProfitable())
            .append("releaseTime", getReleaseTime())
            .append("transactionsTime", getTransactionsTime())
            .append("state", getState())
            .append("paymentScreenshot", getPaymentScreenshot())
            .toString();
    }
}
