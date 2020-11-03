package com.ruoyi.order.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("法币订单")
@Data
public class FrenchCurrencyOrder {

    private Integer id;

    @ApiModelProperty("交易单号")
    private String orderId;

    @ApiModelProperty("交易币种")
    private String transactionCurrency;

    @ApiModelProperty("数量")
    private Integer number;

    @ApiModelProperty("买入来源")
    private String buyingSources;

    @ApiModelProperty("卖出去向")
    private String whereToSell;

    @ApiModelProperty("交易类型")
    private String transactionType;

    @ApiModelProperty("买入数量")
    private BigDecimal purchaseQuantity;

    @ApiModelProperty("买入价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty("出售数量")
    private BigDecimal sellQuantity;

    @ApiModelProperty("出售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty("花费USDT")
    private BigDecimal spendUsdt;

    @ApiModelProperty("可得法币")
    private BigDecimal availableFiatMoney;

    @ApiModelProperty("付款截图")
    private String paymentImg;

    @ApiModelProperty("可收益")
    private BigDecimal profit;

    @ApiModelProperty("订单状态")
    private Integer orderState;

    @ApiModelProperty("买卖类型 1：买 2：卖")
    private Integer businessType;

    @ApiModelProperty("订单时间")
    private Date createTime;

}
