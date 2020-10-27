package com.ruoyi.digital.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 数据列表对象 rc_transaction_info
 * JsonIgnoreProperties
 * 将这个注解写在类上之后，就会忽略类中不存在的字段。这个注解还可以指定要忽略的字段
 * @author xiaoyu
 * @date 2020-10-23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RcTransactionInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 币种的简称唯一 */
    @Excel(name = "币种的简称唯一")
    private String code;

    /** 币种英文名称 */
    @Excel(name = "币种英文名称")
    private String name;

    /** 币种英文名称 */
    @Excel(name = "币种英文名称")
    private String symbol;

    /** 币种中文名称 */
    @Excel(name = "币种中文名称")
    private String fullname;

    /** logo */
    @Excel(name = "logo")
    private String logo;

    /** 币种描述 */
    @Excel(name = "币种描述")
    private String coindesc;

    /** 币种市值人民币 */
    @Excel(name = "币种市值人民币")
    private BigDecimal marketcap;

    /** 币种市值美元 */
    @Excel(name = "币种市值美元")
    private BigDecimal marketcapTotalUsd;

    /** 币种价格美元 */
    @Excel(name = "币种价格美元")
    private BigDecimal price;

    /** 币种价格人民币 */
    @Excel(name = "币种价格人民币")
    private BigDecimal priceCny;

    /** 行情24H涨幅/币种价格差 */
    @Excel(name = "行情24H涨幅/币种价格差")
    private BigDecimal changePercent;

    /** 币种流通量 */
    @Excel(name = "币种流通量")
    private BigDecimal supply;

    /** 币种发行量 */
    @Excel(name = "币种发行量")
    private BigDecimal totalSupply;

    /** 流通量/发行量百分比=流通率 */
    @Excel(name = "流通量/发行量百分比=流通率")
    private BigDecimal circulationRate;

    /** 24H额 */
    @Excel(name = "24H额")
    private BigDecimal amountDay;

    /** 币种最高价 */
    @Excel(name = "币种最高价")
    private BigDecimal high;

    /** 币种最低价 */
    @Excel(name = "币种最低价")
    private BigDecimal low;

    /** 详情24H额 */
    @Excel(name = "详情24H额")
    private BigDecimal vol;

    /** 详情24H换手率/最新上市24H涨幅 */
    @Excel(name = "详情24H换手率/最新上市24H涨幅")
    private BigDecimal turnOver;

    /** 币种上市时间 */
    @Excel(name = "币种上市时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String onlineTime;

    /** 入库操作时间 */
    @Excel(name = "入库操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatatime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getSymbol()
    {
        return symbol;
    }
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public String getName()
    {
        return name;
    }
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getFullname()
    {
        return fullname;
    }
    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getLogo()
    {
        return logo;
    }
    public void setCoindesc(String coindesc)
    {
        this.coindesc = coindesc;
    }

    public String getCoindesc()
    {
        return coindesc;
    }
    public void setMarketcap(BigDecimal marketcap)
    {
        this.marketcap = marketcap;
    }

    public BigDecimal getMarketcap()
    {
        return marketcap;
    }
    public void setMarketcapTotalUsd(BigDecimal marketcapTotalUsd)
    {
        this.marketcapTotalUsd = marketcapTotalUsd;
    }

    public BigDecimal getMarketcapTotalUsd()
    {
        return marketcapTotalUsd;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setPriceCny(BigDecimal priceCny)
    {
        this.priceCny = priceCny;
    }

    public BigDecimal getPriceCny()
    {
        return priceCny;
    }
    public void setChangePercent(BigDecimal changePercent)
    {
        this.changePercent = changePercent;
    }

    public BigDecimal getChangePercent()
    {
        return changePercent;
    }
    public void setSupply(BigDecimal supply)
    {
        this.supply = supply;
    }

    public BigDecimal getSupply()
    {
        return supply;
    }
    public void setTotalSupply(BigDecimal totalSupply)
    {
        this.totalSupply = totalSupply;
    }

    public BigDecimal getTotalSupply()
    {
        return totalSupply;
    }
    public void setCirculationRate(BigDecimal circulationRate)
    {
        this.circulationRate = circulationRate;
    }

    public BigDecimal getCirculationRate()
    {
        return circulationRate;
    }
    public void setAmountDay(BigDecimal amountDay)
    {
        this.amountDay = amountDay;
    }

    public BigDecimal getAmountDay()
    {
        return amountDay;
    }
    public void setHigh(BigDecimal high)
    {
        this.high = high;
    }

    public BigDecimal getHigh()
    {
        return high;
    }
    public void setLow(BigDecimal low)
    {
        this.low = low;
    }

    public BigDecimal getLow()
    {
        return low;
    }
    public void setVol(BigDecimal vol)
    {
        this.vol = vol;
    }

    public BigDecimal getVol()
    {
        return vol;
    }
    public void setTurnOver(BigDecimal turnOver)
    {
        this.turnOver = turnOver;
    }

    public BigDecimal getTurnOver()
    {
        return turnOver;
    }
    public void setOnlineTime(String onlineTime)
    {
        this.onlineTime = onlineTime;
    }

    public String getOnlineTime()
    {
        return onlineTime;
    }

    public void setLastUpdatatime(Date lastUpdatatime)
    {
        this.lastUpdatatime = lastUpdatatime;
    }

    public Date getLastUpdatatime()
    {
        return lastUpdatatime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("code", getCode())
                .append("symbol",getSupply())
                .append("name", getName())
                .append("fullname", getFullname())
                .append("logo", getLogo())
                .append("coindesc", getCoindesc())
                .append("marketcap", getMarketcap())
                .append("marketcapTotalUsd", getMarketcapTotalUsd())
                .append("price", getPrice())
                .append("priceCny", getPriceCny())
                .append("changePercent", getChangePercent())
                .append("supply", getSupply())
                .append("totalSupply", getTotalSupply())
                .append("circulationRate", getCirculationRate())
                .append("amountDay", getAmountDay())
                .append("high", getHigh())
                .append("low", getLow())
                .append("vol", getVol())
                .append("turnOver", getTurnOver())
                .append("onlineTime", getOnlineTime())
                .append("lastUpdatatime", getLastUpdatatime())
                .toString();
    }
}
