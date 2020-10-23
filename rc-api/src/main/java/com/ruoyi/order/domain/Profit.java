package com.ruoyi.order.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户收益实体类")
@Data
public class Profit {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("收益类型 1：法币 2：币币")
    private String profitType;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("已收入")
    private BigDecimal earned;

    @ApiModelProperty("已完成单数")
    private Integer completed;

    @ApiModelProperty("最后修改时间")
    private Date lastTime;
}
