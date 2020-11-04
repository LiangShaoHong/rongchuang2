package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.RcAppeal;

/**
 * 订单申诉Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
public interface RcAppealMapper 
{
    /**
     * 查询订单申诉
     * 
     * @param id 订单申诉ID
     * @return 订单申诉
     */
    public RcAppeal selectRcAppealById(Long id);

    /**
     * 查询订单申诉列表
     * 
     * @param rcAppeal 订单申诉
     * @return 订单申诉集合
     */
    public List<RcAppeal> selectRcAppealList(RcAppeal rcAppeal);

    /**
     * 新增订单申诉
     * 
     * @param rcAppeal 订单申诉
     * @return 结果
     */
    public int insertRcAppeal(RcAppeal rcAppeal);

    /**
     * 修改订单申诉
     * 
     * @param rcAppeal 订单申诉
     * @return 结果
     */
    public int updateRcAppeal(RcAppeal rcAppeal);

    /**
     * 删除订单申诉
     * 
     * @param id 订单申诉ID
     * @return 结果
     */
    public int deleteRcAppealById(Long id);

    /**
     * 批量删除订单申诉
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcAppealByIds(String[] ids);
}
