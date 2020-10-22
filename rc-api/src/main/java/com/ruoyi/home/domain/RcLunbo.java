package com.ruoyi.home.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 rc_lunbo
 * 
 * @author xiaoxia
 * @date 2020-10-22
 */
public class RcLunbo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 图片名字 */
    @Excel(name = "图片名字")
    private String name;

    /** 图片地址 */
    private String img;

    /** 点击是否可以跳转（默认0否1是） */
    @Excel(name = "点击是否可以跳转", readConverterExp = "默=认0否1是")
    private String isHref;

    /** 跳转页面 */
    @Excel(name = "跳转页面")
    private String hrefUrl;

    /** 排序(默认0 数字越大约靠前) */
    @Excel(name = "排序(默认0 数字越大约靠前)")
    private Integer sort;

    /** 是否显示（0否默认1是） */
    @Excel(name = "是否显示", readConverterExp = "0=否默认1是")
    private String isShow;

    /** 是否删除(默认0否1是) */
    private String isDel;

    /** 备注 */
    private String memo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }
    public void setIsHref(String isHref) 
    {
        this.isHref = isHref;
    }

    public String getIsHref() 
    {
        return isHref;
    }
    public void setHrefUrl(String hrefUrl) 
    {
        this.hrefUrl = hrefUrl;
    }

    public String getHrefUrl() 
    {
        return hrefUrl;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setIsShow(String isShow) 
    {
        this.isShow = isShow;
    }

    public String getIsShow() 
    {
        return isShow;
    }
    public void setIsDel(String isDel) 
    {
        this.isDel = isDel;
    }

    public String getIsDel() 
    {
        return isDel;
    }
    public void setMemo(String memo) 
    {
        this.memo = memo;
    }

    public String getMemo() 
    {
        return memo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("img", getImg())
            .append("isHref", getIsHref())
            .append("hrefUrl", getHrefUrl())
            .append("sort", getSort())
            .append("isShow", getIsShow())
            .append("createTime", getCreateTime())
            .append("isDel", getIsDel())
            .append("memo", getMemo())
            .toString();
    }
}
