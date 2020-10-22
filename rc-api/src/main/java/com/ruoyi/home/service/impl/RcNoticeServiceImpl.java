package com.ruoyi.home.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.home.mapper.RcNoticeMapper;
import com.ruoyi.home.domain.RcNotice;
import com.ruoyi.home.service.IRcNoticeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 首页公告Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Service
public class RcNoticeServiceImpl implements IRcNoticeService 
{
    @Autowired
    private RcNoticeMapper rcNoticeMapper;

    /**
     * 查询首页公告
     * 
     * @param id 首页公告ID
     * @return 首页公告
     */
    @Override
    public RcNotice selectRcNoticeById(Long id)
    {
        return rcNoticeMapper.selectRcNoticeById(id);
    }

    /**
     * 查询首页公告列表
     * 
     * @param rcNotice 首页公告
     * @return 首页公告
     */
    @Override
    public List<RcNotice> selectRcNoticeList(RcNotice rcNotice)
    {
        return rcNoticeMapper.selectRcNoticeList(rcNotice);
    }

    /**
     * 新增首页公告
     * 
     * @param rcNotice 首页公告
     * @return 结果
     */
    @Override
    public int insertRcNotice(RcNotice rcNotice)
    {
        rcNotice.setCreateTime(DateUtils.getNowDate());
        return rcNoticeMapper.insertRcNotice(rcNotice);
    }

    /**
     * 修改首页公告
     * 
     * @param rcNotice 首页公告
     * @return 结果
     */
    @Override
    public int updateRcNotice(RcNotice rcNotice)
    {
        return rcNoticeMapper.updateRcNotice(rcNotice);
    }

    /**
     * 删除首页公告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcNoticeByIds(String ids)
    {
        return rcNoticeMapper.deleteRcNoticeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除首页公告信息
     * 
     * @param id 首页公告ID
     * @return 结果
     */
    @Override
    public int deleteRcNoticeById(Long id)
    {
        return rcNoticeMapper.deleteRcNoticeById(id);
    }
}
