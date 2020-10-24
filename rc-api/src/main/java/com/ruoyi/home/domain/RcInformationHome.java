package com.ruoyi.home.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaoxia
 */
@ApiModel("首页信息")
@Data
public class RcInformationHome {
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("系统信息主题")
    private String infoTitle;

    @ApiModelProperty("系统信息内容")
    private String infoContent;

    @ApiModelProperty("系统创建时间")
    private Date createTime;
}
