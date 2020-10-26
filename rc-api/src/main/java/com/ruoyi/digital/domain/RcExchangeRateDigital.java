package com.ruoyi.digital.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 汇率对象 rc_exchange_rate
 * 
 * @author xiaoyu
 * @date 2020-10-26
 */
@ApiModel("汇率")
@Data
public class RcExchangeRateDigital{

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("汇率代码")
    private String exchangeRateCode;

    @ApiModelProperty("汇率名称")
    private String exchangeRateName;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

}
