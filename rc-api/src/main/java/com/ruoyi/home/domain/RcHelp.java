package com.ruoyi.home.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 帮助信息对象 rc_help
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
public class RcHelp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 帮助主题 */
    @Excel(name = "帮助主题")
    private String helpTitle;

    /** 帮助内容 */
    private String helpContent;

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
    public void setHelpTitle(String helpTitle) 
    {
        this.helpTitle = helpTitle;
    }

    public String getHelpTitle() 
    {
        return helpTitle;
    }
    public void setHelpContent(String helpContent) 
    {
        this.helpContent = helpContent;
    }

    public String getHelpContent() 
    {
        return helpContent;
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
            .append("helpTitle", getHelpTitle())
            .append("helpContent", getHelpContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }
}
