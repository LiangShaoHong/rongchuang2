package com.ruoyi.digital.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 汇率对象 rc_exchange_rate
 * 
 * @author xiaoxia
 * @date 2020-10-21
 */
public class RcExchangeRate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 汇率代码 */
    @Excel(name = "汇率代码")
    private String exchangeRateCode;

    /** 汇率名称 */
    @Excel(name = "汇率名称")
    private String exchangeRateName;

    /** 汇率 */
    private BigDecimal exchangeRate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setExchangeRateCode(String exchangeRateCode) 
    {
        this.exchangeRateCode = exchangeRateCode;
    }

    public String getExchangeRateCode() 
    {
        return exchangeRateCode;
    }
    public void setExchangeRateName(String exchangeRateName) 
    {
        this.exchangeRateName = exchangeRateName;
    }

    public String getExchangeRateName() 
    {
        return exchangeRateName;
    }
    public void setExchangeRate(BigDecimal exchangeRate) 
    {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeRate() 
    {
        return exchangeRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("exchangeRateCode", getExchangeRateCode())
            .append("exchangeRateName", getExchangeRateName())
            .append("exchangeRate", getExchangeRate())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
