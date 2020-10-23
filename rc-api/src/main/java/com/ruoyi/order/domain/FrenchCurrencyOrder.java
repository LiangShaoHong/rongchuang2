package com.ruoyi.order.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("法币订单")
@Data
public class FrenchCurrencyOrder {


    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("交易单号")
    private String orderId;//交易单号
    @ApiModelProperty("交易币种")
    private String transactionCurrency;//交易币种
    @ApiModelProperty("数量")
    private Integer number;//数量
    @ApiModelProperty("主交易类型键id")
    private String transactionType;//交易类型
    @ApiModelProperty("买入价格")
    private BigDecimal purchasePrice;//买入价格
    @ApiModelProperty("主键id")
    private BigDecimal sellPrice;//出售价格
    @ApiModelProperty("可收益")
    private BigDecimal profit;//可收益
    @ApiModelProperty("订单状态")
    private Integer orderState;//订单状态
    @ApiModelProperty("订单时间")
    private Date createTime;//订单时间

}
