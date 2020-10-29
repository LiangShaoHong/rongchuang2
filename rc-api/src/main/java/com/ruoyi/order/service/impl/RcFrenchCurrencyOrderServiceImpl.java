package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.service.IRcFrenchCurrencyOrderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 法币订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
@Service
public class RcFrenchCurrencyOrderServiceImpl implements IRcFrenchCurrencyOrderService 
{
    @Autowired
    private RcFrenchCurrencyOrderMapper rcFrenchCurrencyOrderMapper;

    /**
     * 查询法币订单
     * 
     * @param id 法币订单ID
     * @return 法币订单
     */
    @Override
    public RcFrenchCurrencyOrder selectRcFrenchCurrencyOrderById(Long id)
    {
        return rcFrenchCurrencyOrderMapper.selectRcFrenchCurrencyOrderById(id);
    }

    /**
     * 查询法币订单列表
     * 
     * @param rcFrenchCurrencyOrder 法币订单
     * @return 法币订单
     */
    @Override
    public List<RcFrenchCurrencyOrder> selectRcFrenchCurrencyOrderList(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        return rcFrenchCurrencyOrderMapper.selectRcFrenchCurrencyOrderList(rcFrenchCurrencyOrder);
    }

    /**
     * 新增法币订单
     * 
     * @param rcFrenchCurrencyOrder 法币订单
     * @return 结果
     */
    @Override
    public int insertRcFrenchCurrencyOrder(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        rcFrenchCurrencyOrder.setCreateTime(DateUtils.getNowDate());
        return rcFrenchCurrencyOrderMapper.insertRcFrenchCurrencyOrder(rcFrenchCurrencyOrder);
    }

    /**
     * 修改法币订单
     * 
     * @param rcFrenchCurrencyOrder 法币订单
     * @return 结果
     */
    @Override
    public int updateRcFrenchCurrencyOrder(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        return rcFrenchCurrencyOrderMapper.updateRcFrenchCurrencyOrder(rcFrenchCurrencyOrder);
    }

    /**
     * 删除法币订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcFrenchCurrencyOrderByIds(String ids)
    {
        return rcFrenchCurrencyOrderMapper.deleteRcFrenchCurrencyOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除法币订单信息
     * 
     * @param id 法币订单ID
     * @return 结果
     */
    @Override
    public int deleteRcFrenchCurrencyOrderById(Long id)
    {
        return rcFrenchCurrencyOrderMapper.deleteRcFrenchCurrencyOrderById(id);
    }
}
