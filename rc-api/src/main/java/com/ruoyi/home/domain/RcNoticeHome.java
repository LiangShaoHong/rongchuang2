package com.ruoyi.home.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaoxia
 */
@ApiModel("首页公告")
@Data
public class RcNoticeHome {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("公告标题")
    private String noticeTitle;

    @ApiModelProperty("公告内容")
    private String noticeContent;

    @ApiModelProperty("添加时间")
    private String createTime;
}
