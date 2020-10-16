package com.ruoyi.quartz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 交易所信息对象 rc_transaction_platform
 * 
 * @author ruoyi
 * @date 2020-10-16
 */
public class RcTransactionPlatform extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 货币id */
    @Excel(name = "货币id")
    private String coinId;

    /** 交易所名称 */
    @Excel(name = "交易所名称")
    private String name;

    /** 交易所logo */
    @Excel(name = "交易所logo")
    private String logo;

    /** 交易所排行 */
    @Excel(name = "交易所排行")
    private String rank;

    /** 交易对（个） */
    @Excel(name = "交易对", readConverterExp = "个=")
    private String pairnum;

    /** 24H额（美元） */
    @Excel(name = "24H额", readConverterExp = "美=元")
    private BigDecimal volumn;

    /** 24H额（BTC） */
    @Excel(name = "24H额", readConverterExp = "B=TC")
    private BigDecimal volumnBtc;

    /** 24H额（人民币） */
    @Excel(name = "24H额", readConverterExp = "人=民币")
    private BigDecimal volumnCny;

    /** 贸易网址 */
    @Excel(name = "贸易网址")
    private String tradeurl;

    /** 24H涨跌（百分比） */
    @Excel(name = "24H涨跌", readConverterExp = "百=分比")
    private String changeVolumn;

    /** ExRank */
    @Excel(name = "ExRank")
    private String exrank;

    /** 持有资产 */
    @Excel(name = "持有资产")
    private BigDecimal assetsUsd;

    /** 风险等级 */
    @Excel(name = "风险等级")
    private String riskLevel;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCoinId(String coinId) 
    {
        this.coinId = coinId;
    }

    public String getCoinId() 
    {
        return coinId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setLogo(String logo) 
    {
        this.logo = logo;
    }

    public String getLogo() 
    {
        return logo;
    }
    public void setRank(String rank) 
    {
        this.rank = rank;
    }

    public String getRank() 
    {
        return rank;
    }
    public void setPairnum(String pairnum) 
    {
        this.pairnum = pairnum;
    }

    public String getPairnum() 
    {
        return pairnum;
    }
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }

    public BigDecimal getVolumn()
    {
        return volumn;
    }
    public void setVolumnBtc(BigDecimal volumnBtc)
    {
        this.volumnBtc = volumnBtc;
    }

    public BigDecimal getVolumnBtc()
    {
        return volumnBtc;
    }
    public void setVolumnCny(BigDecimal volumnCny)
    {
        this.volumnCny = volumnCny;
    }

    public BigDecimal getVolumnCny()
    {
        return volumnCny;
    }
    public void setTradeurl(String tradeurl) 
    {
        this.tradeurl = tradeurl;
    }

    public String getTradeurl() 
    {
        return tradeurl;
    }
    public void setChangeVolumn(String changeVolumn) 
    {
        this.changeVolumn = changeVolumn;
    }

    public String getChangeVolumn() 
    {
        return changeVolumn;
    }
    public void setExrank(String exrank) 
    {
        this.exrank = exrank;
    }

    public String getExrank()
    {
        return exrank;
    }
    public void setAssetsUsd(BigDecimal assetsUsd)
    {
        this.assetsUsd = assetsUsd;
    }

    public BigDecimal getAssetsUsd()
    {
        return assetsUsd;
    }
    public void setRiskLevel(String riskLevel) 
    {
        this.riskLevel = riskLevel;
    }

    public String getRiskLevel() 
    {
        return riskLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("coinId", getCoinId())
            .append("name", getName())
            .append("logo", getLogo())
            .append("rank", getRank())
            .append("pairnum", getPairnum())
            .append("volumn", getVolumn())
            .append("volumnBtc", getVolumnBtc())
            .append("volumnCny", getVolumnCny())
            .append("tradeurl", getTradeurl())
            .append("changeVolumn", getChangeVolumn())
            .append("exrank", getExrank())
            .append("assetsUsd", getAssetsUsd())
            .append("riskLevel", getRiskLevel())
            .append("createTime", getCreateTime())
            .toString();
    }
}
