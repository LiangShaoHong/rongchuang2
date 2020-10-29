package com.ruoyi.user.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户金额变化记录对象 rc_money_record
 *
 * @author xiaoyu
 * @date 2020-10-29
 */
public class RcMoneyRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /** 交易会员id */
    @Excel(name = "交易会员id")
    private String userId;

    /** 交易会员名称 */
    @Excel(name = "交易会员名称")
    private String userName;

    /** 交易对象id(订单ID) */
    @Excel(name = "交易对象id(订单ID)")
    private String fromUserId;

    /** 金额变化值 */
    @Excel(name = "金额变化值")
    private BigDecimal money;

    /** 变动前金额 */
    @Excel(name = "变动前金额")
    private BigDecimal beforeMoney;

    /** 变动后金额 */
    @Excel(name = "变动后金额")
    private BigDecimal afterMoney;

    /** 手续费(平台佣金) */
    @Excel(name = "手续费(平台佣金)")
    private BigDecimal cashHandFee;

    /** 资金变化类型 0转账 1提现 2充值 3后台人员操作 */
    @Excel(name = "资金变化类型 0转账 1提现 2充值 3后台人员操作")
    private String recordType;

    /** 备注 */
    @Excel(name = "备注")
    private String mark;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setFromUserId(String fromUserId)
    {
        this.fromUserId = fromUserId;
    }

    public String getFromUserId()
    {
        return fromUserId;
    }
    public void setMoney(BigDecimal money)
    {
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }

    public void setBeforeMoney(BigDecimal beforeMoney)
    {
        this.beforeMoney = beforeMoney;
    }

    public BigDecimal getBeforeMoney()
    {
        return beforeMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney)
    {
        this.afterMoney = afterMoney;
    }

    public BigDecimal getAfterMoney()
    {
        return afterMoney;
    }

    public void setCashHandFee(BigDecimal cashHandFee)
    {
        this.cashHandFee = cashHandFee;
    }

    public BigDecimal getCashHandFee()
    {
        return cashHandFee;
    }
    public void setRecordType(String recordType)
    {
        this.recordType = recordType;
    }

    public String getRecordType()
    {
        return recordType;
    }
    public void setMark(String mark)
    {
        this.mark = mark;
    }

    public String getMark()
    {
        return mark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("fromUserId", getFromUserId())
                .append("beforeMoney", getBeforeMoney())
                .append("money", getMoney())
                .append("afterMoney", getAfterMoney())
                .append("cashHandFee", getCashHandFee())
                .append("recordType", getRecordType())
                .append("mark", getMark())
                .toString();
    }

    public RcMoneyRecord(){

    }

    public RcMoneyRecord(String userId,String userName,String fromUserId,BigDecimal beforeMoney,BigDecimal afterMoney,BigDecimal money,BigDecimal cashHandFee,String recordType, String mark){
        this.userId = userId;
        this.userName = userName;
        this.fromUserId = fromUserId;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.money = money;
        this.cashHandFee = cashHandFee;
        this.recordType = recordType;
        this.mark = mark;
    }
}