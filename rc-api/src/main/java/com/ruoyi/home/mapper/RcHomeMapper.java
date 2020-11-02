package com.ruoyi.home.mapper;

import com.ruoyi.home.domain.RcHelpHome;
import com.ruoyi.home.domain.RcInformationHome;
import com.ruoyi.home.domain.RcLunboHome;
import com.ruoyi.home.domain.RcNoticeHome;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author xiaoxia
 */
public interface RcHomeMapper {

    List<RcNoticeHome> getNoticeList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    RcNoticeHome getNoticeDetail(@Param("id") Integer id);
    RcNoticeHome getNewNotice();
    List<RcInformationHome> getInfoList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    List<RcLunboHome> getLunboList();
    List<RcHelpHome> getHelpList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    RcHelpHome getHelpDetail(@Param("id") Integer id);
}
