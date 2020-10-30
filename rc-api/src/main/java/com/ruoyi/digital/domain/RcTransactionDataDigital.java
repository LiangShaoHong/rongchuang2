package com.ruoyi.digital.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实时各交易所币种交易数据对象 rc_transaction_data
 *
 * @author xiaoyu
 * @date 2020-10-26
 */
@ApiModel("市值行情数据")
@Data
public class RcTransactionDataDigital implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("币种英文名称")
    private String name;

    @ApiModelProperty("币种唯一标识码")
    private String code;

    @ApiModelProperty("币种的中文名")
    private String fullname;

    @ApiModelProperty("币种的排名")
    private String rank;

    @ApiModelProperty("币种的logo")
    private String logo;

    @ApiModelProperty("币种最新价美元")
    private BigDecimal currentPriceUsd;

    @ApiModelProperty("市值美元")
    private BigDecimal marketValueUsd;

    @ApiModelProperty("24小时涨幅")
    private String changePercent;

}
