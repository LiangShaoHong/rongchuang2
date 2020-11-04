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
 * 交易所信息对象 rc_transaction_platform
 *
 * @author xiaoyu
 * @date 2020-10-22
 */
@ApiModel("币种详情数据")
@Data
public class RcTransactionPlatformDigital {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("货币id")
    private String coinId;

    @ApiModelProperty("交易所名称")
    private String name;

    @ApiModelProperty("交易所logo")
    private String logo;

    @ApiModelProperty("交易所排行")
    private String rank;

    @ApiModelProperty("交易对（个）")
    private String pairnum;

    @ApiModelProperty("24H额（美元）")
    private BigDecimal volumn;

    @ApiModelProperty("24H额（BTC）")
    private BigDecimal volumnBtc;

    @ApiModelProperty("24H额（人民币）")
    private BigDecimal volumnCny;

    @ApiModelProperty("贸易网址 ")
    private String tradeurl;

    @ApiModelProperty("24H涨跌（百分比）")
    private BigDecimal changeVolumn;

    @ApiModelProperty("ExRank ")
    private String exrank;

    @ApiModelProperty("持有资产 ")
    private BigDecimal assetsUsd;

    @ApiModelProperty("风险等级 ")
    private String riskLevel;

}
