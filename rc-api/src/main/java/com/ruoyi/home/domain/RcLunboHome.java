package com.ruoyi.home.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaoxia
 */
@ApiModel("首页轮播")
@Data
public class RcLunboHome {
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("图片名字")
    private String name;

    @ApiModelProperty("图片路径")
    private String img;

    @ApiModelProperty("点击是否可以跳转")
    private String isHref;

    @ApiModelProperty("跳转页面")
    private String hrefUrl;

    @ApiModelProperty("排序")
    private Integer sort;
}
