package com.ruoyi.order.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class Appeal {

    /**
     *
     */
    private Long id;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 法币订单id
     */
    @Excel(name = "法币订单id")
    private String orderId;

    /**
     * 申诉理由
     */
    @Excel(name = "申诉理由")
    private String appealContent;

    /**
     * 回复内容
     */
    @Excel(name = "回复内容")
    private String replyContent;

    /**
     * 申诉截图
     */
    @Excel(name = "申诉截图")
    private String complImg;

    /**
     * 申诉状态：1-申诉中 2-申诉成功 3-申诉失败
     */
    @Excel(name = "申诉状态：1-申诉中 2-申诉成功 3-申诉失败")
    private Integer state;

    /**
     * 审核时间
     */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;
}
