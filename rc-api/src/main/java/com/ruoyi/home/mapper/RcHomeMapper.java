package com.ruoyi.home.mapper;

import com.ruoyi.order.domain.Profit;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * @author xiaoxia
 */
public interface RcHomeMapper {

    Profit getNoticeList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    Profit getNoticeDetail(@Param("id") Integer id);
    Profit getNewNotice();

}
