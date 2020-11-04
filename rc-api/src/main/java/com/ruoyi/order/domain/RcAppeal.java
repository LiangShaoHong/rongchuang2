package com.ruoyi.order.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单申诉对象 rc_appeal
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
public class RcAppeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 法币订单id */
    @Excel(name = "法币订单id")
    private String orderId;

    /** 申诉理由 */
    @Excel(name = "申诉理由")
    private String appealContent;

    /** 回复内容 */
    @Excel(name = "回复内容")
    private String replyContent;

    /** 申诉截图 */
    @Excel(name = "申诉截图")
    private String complImg;

    /** 申诉状态：1-申诉中 2-申诉成功 3-申诉失败 */
    @Excel(name = "申诉状态：1-申诉中 2-申诉成功 3-申诉失败")
    private Integer state;

    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setAppealContent(String appealContent) 
    {
        this.appealContent = appealContent;
    }

    public String getAppealContent() 
    {
        return appealContent;
    }
    public void setReplyContent(String replyContent) 
    {
        this.replyContent = replyContent;
    }

    public String getReplyContent() 
    {
        return replyContent;
    }
    public void setComplImg(String complImg) 
    {
        this.complImg = complImg;
    }

    public String getComplImg() 
    {
        return complImg;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }
    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("orderId", getOrderId())
            .append("appealContent", getAppealContent())
            .append("replyContent", getReplyContent())
            .append("complImg", getComplImg())
            .append("state", getState())
            .append("auditTime", getAuditTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
