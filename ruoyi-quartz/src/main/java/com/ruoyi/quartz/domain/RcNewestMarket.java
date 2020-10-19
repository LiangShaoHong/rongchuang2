package com.ruoyi.quartz.domain;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 最新上市对象 rc_newest_market
 *
 * @author ruoyi
 * @date 2020-10-16
 */
public class RcNewestMarket extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 币种 */
    @Excel(name = "币种")
    private String coinType;

    /** 全球指数 */
    @Excel(name = "全球指数")
    private String worldExponent;

    /** 24h涨幅 */
    @Excel(name = "24h涨幅")
    private String dz24h;

    /** 成交额 */
    @Excel(name = "成交额")
    private String commitE;

    /** 汇率 */
    @Excel(name = "汇率")
    private String rate;

    /** 上市时间 */
    @Excel(name = "上市时间")
    private String marketTime;

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
    public void setDz24h(String dz24h)
    {
        this.dz24h = dz24h;
    }

    public String getDz24h()
    {
        return dz24h;
    }
    public void setCommitE(String commitE)
    {
        this.commitE = commitE;
    }

    public String getCommitE()
    {
        return commitE;
    }
    public void setRate(String rate)
    {
        this.rate = rate;
    }

    public String getRate()
    {
        return rate;
    }
    public void setMarketTime(String marketTime)
    {
        this.marketTime = marketTime;
    }

    public String getMarketTime()
    {
        return marketTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("coinType", getCoinType())
                .append("worldExponent", getWorldExponent())
                .append("dz24h", getDz24h())
                .append("commitE", getCommitE())
                .append("rate", getRate())
                .append("marketTime", getMarketTime())
                .toString();
    }
}