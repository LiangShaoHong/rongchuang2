package com.ruoyi.notice.service;

import java.util.List;
import com.ruoyi.notice.domain.RcNotice;

/**
 * 首页公告Service接口
 * 
 * @author ruoyi
 * @date 2020-10-21
 */
public interface IRcNoticeService 
{
    /**
     * 查询首页公告
     * 
     * @param id 首页公告ID
     * @return 首页公告
     */
    public RcNotice selectRcNoticeById(Long id);

    /**
     * 查询首页公告列表
     * 
     * @param rcNotice 首页公告
     * @return 首页公告集合
     */
    public List<RcNotice> selectRcNoticeList(RcNotice rcNotice);

    /**
     * 新增首页公告
     * 
     * @param rcNotice 首页公告
     * @return 结果
     */
    public int insertRcNotice(RcNotice rcNotice);

    /**
     * 修改首页公告
     * 
     * @param rcNotice 首页公告
     * @return 结果
     */
    public int updateRcNotice(RcNotice rcNotice);

    /**
     * 批量删除首页公告
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcNoticeByIds(String ids);

    /**
     * 删除首页公告信息
     * 
     * @param id 首页公告ID
     * @return 结果
     */
    public int deleteRcNoticeById(Long id);
}
