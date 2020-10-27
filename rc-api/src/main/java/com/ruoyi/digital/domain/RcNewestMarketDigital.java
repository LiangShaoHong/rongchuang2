package com.ruoyi.digital.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 最新上市对象 rc_newest_market
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
@ApiModel("上市数据")
@Data
public class RcNewestMarketDigital {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("币种名称")
    private String code;

    @ApiModelProperty("币种简称")
    private String name;

    @ApiModelProperty("图标")
    private String logo;

    @ApiModelProperty("全名")
    private String fullName;

    @ApiModelProperty("上市时间")
    private String time;

    @ApiModelProperty("全球指数")
    private BigDecimal price;

    @ApiModelProperty("全球指数兑换人民币")
    private BigDecimal priceCny;

    @ApiModelProperty("24H涨幅")
    private BigDecimal changePercent;

}
