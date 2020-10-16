package com.ruoyi.quartz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实时各交易所币种交易数据对象 rc_transaction_data
 *
 * @author ruoyi
 * @date 2020-10-15
 */
public class RcTransactionData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 币种英文名称 */
    @Excel(name = "币种英文名称")
    private String name;

    /** 币种的简称 */
    @Excel(name = "币种的简称")
    private String symbol;

    /** 币种的排名 */
    @Excel(name = "币种的排名")
    private String rank;

    /** 币种的logo（webp格式） */
    @Excel(name = "币种的logo", readConverterExp = "w=ebp格式")
    private String logo;

    /** 币种的logo（非webp格式） */
    @Excel(name = "币种的logo", readConverterExp = "非=webp格式")
    private String logoPng;

    /** 最新价格（单位：美元） */
    @Excel(name = "最新价格", readConverterExp = "单=位：美元")
    private String priceUsd;

    /** 最新价格（单位：BTC） */
    @Excel(name = "最新价格", readConverterExp = "单=位：BTC")
    private String priceBtc;

    /** 24h的成交额（单位：美元） */
    @Excel(name = "24h的成交额", readConverterExp = "单=位：美元")
    private String volume24hUsd;

    /** 流通市值（单位：美元） */
    @Excel(name = "流通市值", readConverterExp = "单=位：美元")
    private String marketCapUsd;

    /** 流通数量 */
    @Excel(name = "流通数量")
    private String availableSupply;

    /** 总发行量 */
    @Excel(name = "总发行量")
    private String totalSupply;

    /** 最大发行量（最大发行量可能>总发行量，譬如有些币种会主动销毁一部分数量） */
    @Excel(name = "最大发行量", readConverterExp = "最=大发行量可能>总发行量，譬如有些币种会主动销毁一部分数量")
    private String maxSupply;

    /** 1小时涨跌幅 */
    @Excel(name = "1小时涨跌幅")
    private String percentChange1h;

    /** 24小时涨跌幅 */
    @Excel(name = "24小时涨跌幅")
    private String percentChange24h;

    /** 7天涨跌幅 */
    @Excel(name = "7天涨跌幅")
    private String percentChange7d;

    /** 行情更新时间（10位unix时间戳） */
    @Excel(name = "行情更新时间", readConverterExp = "1=0位unix时间戳")
    private String lastUpdated;

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
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSymbol()
    {
        return symbol;
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
    public void setLogoPng(String logoPng)
    {
        this.logoPng = logoPng;
    }

    public String getLogoPng()
    {
        return logoPng;
    }
    public void setPriceUsd(String priceUsd)
    {
        this.priceUsd = priceUsd;
    }

    public String getPriceUsd()
    {
        return priceUsd;
    }
    public void setPriceBtc(String priceBtc)
    {
        this.priceBtc = priceBtc;
    }

    public String getPriceBtc()
    {
        return priceBtc;
    }
    public void setVolume24hUsd(String volume24hUsd)
    {
        this.volume24hUsd = volume24hUsd;
    }

    public String getVolume24hUsd()
    {
        return volume24hUsd;
    }
    public void setMarketCapUsd(String marketCapUsd)
    {
        this.marketCapUsd = marketCapUsd;
    }

    public String getMarketCapUsd()
    {
        return marketCapUsd;
    }
    public void setAvailableSupply(String availableSupply)
    {
        this.availableSupply = availableSupply;
    }

    public String getAvailableSupply()
    {
        return availableSupply;
    }
    public void setTotalSupply(String totalSupply)
    {
        this.totalSupply = totalSupply;
    }

    public String getTotalSupply()
    {
        return totalSupply;
    }
    public void setMaxSupply(String maxSupply)
    {
        this.maxSupply = maxSupply;
    }

    public String getMaxSupply()
    {
        return maxSupply;
    }
    public void setPercentChange1h(String percentChange1h)
    {
        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange1h()
    {
        return percentChange1h;
    }
    public void setPercentChange24h(String percentChange24h)
    {
        this.percentChange24h = percentChange24h;
    }

    public String getPercentChange24h()
    {
        return percentChange24h;
    }
    public void setPercentChange7d(String percentChange7d)
    {
        this.percentChange7d = percentChange7d;
    }

    public String getPercentChange7d()
    {
        return percentChange7d;
    }
    public void setLastUpdated(String lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdated()
    {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("symbol", getSymbol())
                .append("rank", getRank())
                .append("logo", getLogo())
                .append("logoPng", getLogoPng())
                .append("priceUsd", getPriceUsd())
                .append("priceBtc", getPriceBtc())
                .append("volume24hUsd", getVolume24hUsd())
                .append("marketCapUsd", getMarketCapUsd())
                .append("availableSupply", getAvailableSupply())
                .append("totalSupply", getTotalSupply())
                .append("maxSupply", getMaxSupply())
                .append("percentChange1h", getPercentChange1h())
                .append("percentChange24h", getPercentChange24h())
                .append("percentChange7d", getPercentChange7d())
                .append("lastUpdated", getLastUpdated())
                .toString();
    }
}