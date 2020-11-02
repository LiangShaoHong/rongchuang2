package com.ruoyi.user.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 用户认证对象 rc_auth
 * 
 * @author xiaoyu
 * @date 2020-10-30
 */
@ApiModel("用户认证对象")
@Data
public class RcApiAuth{

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userid;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("国籍")
    private String nationality;

    @ApiModelProperty("证件类型")
    private String documentType;

    @ApiModelProperty("证件号码")
    private String certificateNum;

    @ApiModelProperty("证件截图")
    private String certificateImg;

    @ApiModelProperty("审核状态")
    private Long status;

    @ApiModelProperty("提交时间")
    private Date submitTime;

    @ApiModelProperty("审核时间")
    private Date reviewTime;

}
