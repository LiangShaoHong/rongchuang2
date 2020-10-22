package com.ruoyi.home.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.home.mapper.RcLunboMapper;
import com.ruoyi.home.domain.RcLunbo;
import com.ruoyi.home.service.IRcLunboService;
import com.ruoyi.common.core.text.Convert;

/**
 * 轮播图Service业务层处理
 * 
 * @author xiaoxia
 * @date 2020-10-22
 */
@Service
public class RcLunboServiceImpl implements IRcLunboService 
{
    @Autowired
    private RcLunboMapper rcLunboMapper;

    /**
     * 查询轮播图
     * 
     * @param id 轮播图ID
     * @return 轮播图
     */
    @Override
    public RcLunbo selectRcLunboById(Long id)
    {
        return rcLunboMapper.selectRcLunboById(id);
    }

    /**
     * 查询轮播图列表
     * 
     * @param rcLunbo 轮播图
     * @return 轮播图
     */
    @Override
    public List<RcLunbo> selectRcLunboList(RcLunbo rcLunbo)
    {
        return rcLunboMapper.selectRcLunboList(rcLunbo);
    }

    /**
     * 新增轮播图
     * 
     * @param rcLunbo 轮播图
     * @return 结果
     */
    @Override
    public int insertRcLunbo(RcLunbo rcLunbo)
    {
        rcLunbo.setCreateTime(DateUtils.getNowDate());
        return rcLunboMapper.insertRcLunbo(rcLunbo);
    }

    /**
     * 修改轮播图
     * 
     * @param rcLunbo 轮播图
     * @return 结果
     */
    @Override
    public int updateRcLunbo(RcLunbo rcLunbo)
    {
        return rcLunboMapper.updateRcLunbo(rcLunbo);
    }

    /**
     * 删除轮播图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcLunboByIds(String ids)
    {
        return rcLunboMapper.deleteRcLunboByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除轮播图信息
     * 
     * @param id 轮播图ID
     * @return 结果
     */
    @Override
    public int deleteRcLunboById(Long id)
    {
        return rcLunboMapper.deleteRcLunboById(id);
    }
}
