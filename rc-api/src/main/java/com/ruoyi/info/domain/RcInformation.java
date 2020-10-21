package com.ruoyi.info.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 系统信息对象 rc_information
 * 
 * @author ruoyi
 * @date 2020-10-21
 */
public class RcInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 系统信息主题 */
    @Excel(name = "系统信息主题")
    private String infoTitle;

    /** 系统信息内容 */
    @Excel(name = "系统信息内容")
    private String infoContent;

    /** 接收用户 */
    @Excel(name = "接收用户")
    private String infoUser;

    /** 状态 */
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInfoTitle(String infoTitle) 
    {
        this.infoTitle = infoTitle;
    }

    public String getInfoTitle() 
    {
        return infoTitle;
    }
    public void setInfoContent(String infoContent) 
    {
        this.infoContent = infoContent;
    }

    public String getInfoContent() 
    {
        return infoContent;
    }
    public void setInfoUser(String infoUser) 
    {
        this.infoUser = infoUser;
    }

    public String getInfoUser() 
    {
        return infoUser;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("infoTitle", getInfoTitle())
            .append("infoContent", getInfoContent())
            .append("createBy", getCreateBy())
            .append("infoUser", getInfoUser())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }
}
