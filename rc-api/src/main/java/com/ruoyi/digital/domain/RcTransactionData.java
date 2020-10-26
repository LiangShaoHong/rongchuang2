package com.ruoyi.digital.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实时各交易所币种交易数据对象 rc_transaction_data
 *
 * @author xiaoyu
 * @date 2020-10-26
 */
public class RcTransactionData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 币种英文名称 */
    @Excel(name = "币种英文名称")
    private String name;

    /** 币种唯一标识码 */
    @Excel(name = "币种唯一标识码")
    private String code;

    /** 币种的中文名 */
    @Excel(name = "币种的中文名")
    private String fullname;

    /** 币种的排名 */
    @Excel(name = "币种的排名")
    private String rank;

    /** 币种的logo（webp格式） */
    @Excel(name = "币种的logo", readConverterExp = "w=ebp格式")
    private String logo;

    /** 币种最新价人民币 */
    @Excel(name = "币种最新价人民币")
    private BigDecimal currentPrice;

    /** 币种最新价美元 */
    @Excel(name = "币种最新价美元")
    private BigDecimal currentPriceUsd;

    /** 市值人民币 */
    @Excel(name = "市值人民币")
    private BigDecimal marketValue;

    /** 市值美元 */
    @Excel(name = "市值美元")
    private BigDecimal marketValueUsd;

    /** 24小时涨幅 */
    @Excel(name = "24小时涨幅")
    private String changePercent;

    /** 行情更新时间（10位unix时间戳） */
    @Excel(name = "行情更新时间", readConverterExp = "1=0位unix时间戳")
    private Date lastUpdated;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getFullname()
    {
        return fullname;
    }
    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public String getRank()
    {
        return rank;
    }
    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getLogo()
    {
        return logo;
    }
    public void setCurrentPrice(BigDecimal currentPrice)
    {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getCurrentPrice()
    {
        return currentPrice;
    }
    public void setCurrentPriceUsd(BigDecimal currentPriceUsd)
    {
        this.currentPriceUsd = currentPriceUsd;
    }

    public BigDecimal getCurrentPriceUsd()
    {
        return currentPriceUsd;
    }
    public void setMarketValue(BigDecimal marketValue)
    {
        this.marketValue = marketValue;
    }

    public BigDecimal getMarketValue()
    {
        return marketValue;
    }
    public void setMarketValueUsd(BigDecimal marketValueUsd)
    {
        this.marketValueUsd = marketValueUsd;
    }

    public BigDecimal getMarketValueUsd()
    {
        return marketValueUsd;
    }
    public void setChangePercent(String changePercent)
    {
        this.changePercent = changePercent;
    }

    public String getChangePercent()
    {
        return changePercent;
    }
    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("code", getCode())
                .append("fullname", getFullname())
                .append("rank", getRank())
                .append("logo", getLogo())
                .append("currentPrice", getCurrentPrice())
                .append("currentPriceUsd", getCurrentPriceUsd())
                .append("marketValue", getMarketValue())
                .append("marketValueUsd", getMarketValueUsd())
                .append("changePercent", getChangePercent())
                .append("lastUpdated", getLastUpdated())
                .toString();
    }
}
