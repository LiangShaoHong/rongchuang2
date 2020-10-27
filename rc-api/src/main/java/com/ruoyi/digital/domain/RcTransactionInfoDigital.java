package com.ruoyi.digital.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据列表对象 rc_transaction_info
 *
 * @author xiaoyu
 * @date 2020-10-23
 */
@ApiModel("币种详情数据")
@Data
public class RcTransactionInfoDigital {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("币种的简称唯一")
    private String code;

    @ApiModelProperty("币种英文名称")
    private String name;

    @ApiModelProperty("币种中文名称")
    private String fullname;

    @ApiModelProperty("币种简称")
    private String symbol;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("币种描述")
    private String coindesc;

    @ApiModelProperty("币种市值人民币")
    private BigDecimal marketcap;

    @ApiModelProperty("币种市值美元")
    private BigDecimal marketcapTotalUsd;

    @ApiModelProperty("币种价格美元")
    private BigDecimal price;

    @ApiModelProperty("币种价格人民币")
    private BigDecimal priceCny;

    @ApiModelProperty("行情24H涨幅/币种价格差")
    private BigDecimal changePercent;

    @ApiModelProperty("币种流通量")
    private BigDecimal supply;

    @ApiModelProperty("币种发行量")
    private BigDecimal totalSupply;

    @ApiModelProperty("流通量/发行量百分比=流通率")
    private BigDecimal circulationRate;

    @ApiModelProperty("24H额")
    private BigDecimal amountDay;

    @ApiModelProperty("币种最高价")
    private BigDecimal high;

    @ApiModelProperty("币种最低价")
    private BigDecimal low;

    @ApiModelProperty("详情24H额")
    private BigDecimal vol;

    @ApiModelProperty("详情24H换手率/最新上市24H涨幅")
    private BigDecimal turnOver;

    @ApiModelProperty("币种上市时间")
    private String onlineTime;

}
