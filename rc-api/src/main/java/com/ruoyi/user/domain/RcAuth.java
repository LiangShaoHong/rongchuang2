package com.ruoyi.user.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户认证对象 rc_auth
 * 
 * @author xiaoyu
 * @date 2020-10-30
 */
public class RcAuth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    private Long userid;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 国籍 */
    @Excel(name = "国籍")
    private String nationality;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String documentType;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String certificateNum;

    /** 证件截图 */
    private String certificateImg;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private Long status;

    /** 提交时间 */
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserid(Long userid) 
    {
        this.userid = userid;
    }

    public Long getUserid() 
    {
        return userid;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setNationality(String nationality) 
    {
        this.nationality = nationality;
    }

    public String getNationality() 
    {
        return nationality;
    }
    public void setDocumentType(String documentType) 
    {
        this.documentType = documentType;
    }

    public String getDocumentType() 
    {
        return documentType;
    }
    public void setCertificateNum(String certificateNum) 
    {
        this.certificateNum = certificateNum;
    }

    public String getCertificateNum() 
    {
        return certificateNum;
    }
    public void setCertificateImg(String certificateImg) 
    {
        this.certificateImg = certificateImg;
    }

    public String getCertificateImg() 
    {
        return certificateImg;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
    }
    public void setReviewTime(Date reviewTime) 
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() 
    {
        return reviewTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userid", getUserid())
            .append("name", getName())
            .append("nationality", getNationality())
            .append("documentType", getDocumentType())
            .append("certificateNum", getCertificateNum())
            .append("certificateImg", getCertificateImg())
            .append("status", getStatus())
            .append("submitTime", getSubmitTime())
            .append("reviewTime", getReviewTime())
            .toString();
    }
}
