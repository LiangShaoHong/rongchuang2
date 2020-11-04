package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.RcAppealMapper;
import com.ruoyi.order.domain.RcAppeal;
import com.ruoyi.order.service.IRcAppealService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单申诉Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
@Service
public class RcAppealServiceImpl implements IRcAppealService 
{
    @Autowired
    private RcAppealMapper rcAppealMapper;

    /**
     * 查询订单申诉
     * 
     * @param id 订单申诉ID
     * @return 订单申诉
     */
    @Override
    public RcAppeal selectRcAppealById(Long id)
    {
        return rcAppealMapper.selectRcAppealById(id);
    }

    /**
     * 查询订单申诉列表
     * 
     * @param rcAppeal 订单申诉
     * @return 订单申诉
     */
    @Override
    public List<RcAppeal> selectRcAppealList(RcAppeal rcAppeal)
    {
        return rcAppealMapper.selectRcAppealList(rcAppeal);
    }

    /**
     * 新增订单申诉
     * 
     * @param rcAppeal 订单申诉
     * @return 结果
     */
    @Override
    public int insertRcAppeal(RcAppeal rcAppeal)
    {
        rcAppeal.setCreateTime(DateUtils.getNowDate());
        return rcAppealMapper.insertRcAppeal(rcAppeal);
    }

    /**
     * 修改订单申诉
     * 
     * @param rcAppeal 订单申诉
     * @return 结果
     */
    @Override
    public int updateRcAppeal(RcAppeal rcAppeal)
    {
        return rcAppealMapper.updateRcAppeal(rcAppeal);
    }

    /**
     * 删除订单申诉对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcAppealByIds(String ids)
    {
        return rcAppealMapper.deleteRcAppealByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单申诉信息
     * 
     * @param id 订单申诉ID
     * @return 结果
     */
    @Override
    public int deleteRcAppealById(Long id)
    {
        return rcAppealMapper.deleteRcAppealById(id);
    }
}
