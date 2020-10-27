package com.ruoyi.digital.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 最新上市对象 rc_newest_market
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
public class RcNewestMarket extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 币种名称 */
    @Excel(name = "币种名称")
    private String code;

    /** 币种简称 */
    @Excel(name = "币种简称")
    private String name;

    /** 市场 */
    @Excel(name = "市场")
    private String market;

    /**  */
    @Excel(name = "")
    private String platform;

    /**  */
    @Excel(name = "")
    private String platformName;

    /** 上市时间 */
    @Excel(name = "上市时间")
    private String time;

    /** 全球指数 */
    @Excel(name = "全球指数")
    private BigDecimal price;

    /** 全球指数兑换人民币 */
    @Excel(name = "全球指数兑换人民币")
    private BigDecimal priceCny;

    /**  */
    @Excel(name = "")
    private String volumn;

    /** 24H涨幅 */
    @Excel(name = "24H涨幅")
    private String changePercent;

    /**  */
    @Excel(name = "")
    private String fullNameZh;

    /**  */
    @Excel(name = "")
    private String volUsd;

    /** 图标 */
    @Excel(name = "图标")
    private String logo;

    /** 全名 */
    @Excel(name = "全名")
    private String fullName;

    /**  */
    @Excel(name = "")
    private String circualing;

    /**  */
    @Excel(name = "")
    private String isMineable;

    /**  */
    @Excel(name = "")
    private String changerateUtc;

    /**  */
    @Excel(name = "")
    private String changerateUtc8;

    /** 最后修改时间 */
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdate;

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

    public String getName() 
    {
        return name;
    }
    public void setMarket(String market) 
    {
        this.market = market;
    }

    public String getMarket() 
    {
        return market;
    }
    public void setPlatform(String platform) 
    {
        this.platform = platform;
    }

    public String getPlatform() 
    {
        return platform;
    }
    public void setPlatformName(String platformName) 
    {
        this.platformName = platformName;
    }

    public String getPlatformName() 
    {
        return platformName;
    }
    public void setTime(String time) 
    {
        this.time = time;
    }

    public String getTime() 
    {
        return time;
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
    public void setVolumn(String volumn) 
    {
        this.volumn = volumn;
    }

    public String getVolumn() 
    {
        return volumn;
    }
    public void setChangePercent(String changePercent)
    {
        this.changePercent = changePercent;
    }

    public String getChangePercent()
    {
        return changePercent;
    }
    public void setFullNameZh(String fullNameZh) 
    {
        this.fullNameZh = fullNameZh;
    }

    public String getFullNameZh() 
    {
        return fullNameZh;
    }
    public void setVolUsd(String volUsd) 
    {
        this.volUsd = volUsd;
    }

    public String getVolUsd() 
    {
        return volUsd;
    }
    public void setLogo(String logo) 
    {
        this.logo = logo;
    }

    public String getLogo() 
    {
        return logo;
    }
    public void setFullName(String fullName) 
    {
        this.fullName = fullName;
    }

    public String getFullName() 
    {
        return fullName;
    }
    public void setCircualing(String circualing) 
    {
        this.circualing = circualing;
    }

    public String getCircualing() 
    {
        return circualing;
    }
    public void setIsMineable(String isMineable) 
    {
        this.isMineable = isMineable;
    }

    public String getIsMineable() 
    {
        return isMineable;
    }
    public void setChangerateUtc(String changerateUtc) 
    {
        this.changerateUtc = changerateUtc;
    }

    public String getChangerateUtc() 
    {
        return changerateUtc;
    }
    public void setChangerateUtc8(String changerateUtc8) 
    {
        this.changerateUtc8 = changerateUtc8;
    }

    public String getChangerateUtc8() 
    {
        return changerateUtc8;
    }
    public void setLastUpdate(Date lastUpdate) 
    {
        this.lastUpdate = lastUpdate;
    }

    public Date getLastUpdate() 
    {
        return lastUpdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("market", getMarket())
            .append("platform", getPlatform())
            .append("platformName", getPlatformName())
            .append("time", getTime())
            .append("price", getPrice())
            .append("priceCny", getPriceCny())
            .append("volumn", getVolumn())
            .append("changePercent", getChangePercent())
            .append("fullNameZh", getFullNameZh())
            .append("volUsd", getVolUsd())
            .append("logo", getLogo())
            .append("fullName", getFullName())
            .append("circualing", getCircualing())
            .append("isMineable", getIsMineable())
            .append("changerateUtc", getChangerateUtc())
            .append("changerateUtc8", getChangerateUtc8())
            .append("lastUpdate", getLastUpdate())
            .toString();
    }
}
