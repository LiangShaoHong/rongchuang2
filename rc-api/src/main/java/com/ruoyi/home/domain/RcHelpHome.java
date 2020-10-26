package com.ruoyi.home.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaoxia
 */
@ApiModel("首页帮助")
@Data
public class RcHelpHome {
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("帮助主题")
    private String helpTitle;

    @ApiModelProperty("帮助内容")
    private String helpContent;

    @ApiModelProperty("系统创建时间")
    private Date createTime;
}
