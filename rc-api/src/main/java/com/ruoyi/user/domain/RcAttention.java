package com.ruoyi.user.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户关注对象 rc_attention
 * 
 * @author xiaoyu
 * @date 2020-11-03
 */
public class RcAttention extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 币种 */
    @Excel(name = "币种")
    private String coinType;

    /** 全球指数 */
    @Excel(name = "全球指数")
    private String worldExponent;

    /** 24h涨幅 */
    @Excel(name = "24h涨幅")
    private String dayDz;

    /** 汇率 */
    @Excel(name = "汇率")
    private String rate;

    /** 关注状态 默认0关注 1取消关注 */
    @Excel(name = "关注状态 默认0关注 1取消关注")
    private Integer status;

    /** 关注用户 */
    @Excel(name = "关注用户")
    private Long userId;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCoinType(String coinType)
    {
        this.coinType = coinType;
    }

    public String getCoinType()
    {
        return coinType;
    }
    public void setWorldExponent(String worldExponent)
    {
        this.worldExponent = worldExponent;
    }

    public String getWorldExponent()
    {
        return worldExponent;
    }
    public void setDayDz(String dayDz)
    {
        this.dayDz = dayDz;
    }

    public String getDayDz()
    {
        return dayDz;
    }
    public void setRate(String rate)
    {
        this.rate = rate;
    }

    public String getRate()
    {
        return rate;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setLastTime(Date lastTime)
    {
        this.lastTime = lastTime;
    }

    public Date getLastTime()
    {
        return lastTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("coinType", getCoinType())
                .append("worldExponent", getWorldExponent())
                .append("dayDz", getDayDz())
                .append("rate", getRate())
                .append("status", getStatus())
                .append("userId", getUserId())
                .append("lastTime", getLastTime())
                .toString();
    }
}