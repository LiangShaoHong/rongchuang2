package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.RcCurrencyOrderMapper;
import com.ruoyi.order.domain.RcCurrencyOrder;
import com.ruoyi.order.service.IRcCurrencyOrderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 币币订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
@Service
public class RcCurrencyOrderServiceImpl implements IRcCurrencyOrderService 
{
    @Autowired
    private RcCurrencyOrderMapper rcCurrencyOrderMapper;

    /**
     * 查询币币订单
     * 
     * @param id 币币订单ID
     * @return 币币订单
     */
    @Override
    public RcCurrencyOrder selectRcCurrencyOrderById(Long id)
    {
        return rcCurrencyOrderMapper.selectRcCurrencyOrderById(id);
    }

    /**
     * 查询币币订单列表
     * 
     * @param rcCurrencyOrder 币币订单
     * @return 币币订单
     */
    @Override
    public List<RcCurrencyOrder> selectRcCurrencyOrderList(RcCurrencyOrder rcCurrencyOrder)
    {
        return rcCurrencyOrderMapper.selectRcCurrencyOrderList(rcCurrencyOrder);
    }

    /**
     * 新增币币订单
     * 
     * @param rcCurrencyOrder 币币订单
     * @return 结果
     */
    @Override
    public int insertRcCurrencyOrder(RcCurrencyOrder rcCurrencyOrder)
    {
        rcCurrencyOrder.setCreateTime(DateUtils.getNowDate());
        return rcCurrencyOrderMapper.insertRcCurrencyOrder(rcCurrencyOrder);
    }

    /**
     * 修改币币订单
     * 
     * @param rcCurrencyOrder 币币订单
     * @return 结果
     */
    @Override
    public int updateRcCurrencyOrder(RcCurrencyOrder rcCurrencyOrder)
    {
        return rcCurrencyOrderMapper.updateRcCurrencyOrder(rcCurrencyOrder);
    }

    /**
     * 删除币币订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcCurrencyOrderByIds(String ids)
    {
        return rcCurrencyOrderMapper.deleteRcCurrencyOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除币币订单信息
     * 
     * @param id 币币订单ID
     * @return 结果
     */
    @Override
    public int deleteRcCurrencyOrderById(Long id)
    {
        return rcCurrencyOrderMapper.deleteRcCurrencyOrderById(id);
    }
}
