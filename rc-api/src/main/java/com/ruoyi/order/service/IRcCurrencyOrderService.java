package com.ruoyi.order.service;

import java.util.List;
import com.ruoyi.order.domain.RcCurrencyOrder;

/**
 * 币币订单Service接口
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
public interface IRcCurrencyOrderService 
{
    /**
     * 查询币币订单
     * 
     * @param id 币币订单ID
     * @return 币币订单
     */
    public RcCurrencyOrder selectRcCurrencyOrderById(Long id);

    /**
     * 查询币币订单列表
     * 
     * @param rcCurrencyOrder 币币订单
     * @return 币币订单集合
     */
    public List<RcCurrencyOrder> selectRcCurrencyOrderList(RcCurrencyOrder rcCurrencyOrder);

    /**
     * 新增币币订单
     * 
     * @param rcCurrencyOrder 币币订单
     * @return 结果
     */
    public int insertRcCurrencyOrder(RcCurrencyOrder rcCurrencyOrder);

    /**
     * 修改币币订单
     * 
     * @param rcCurrencyOrder 币币订单
     * @return 结果
     */
    public int updateRcCurrencyOrder(RcCurrencyOrder rcCurrencyOrder);

    /**
     * 批量删除币币订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcCurrencyOrderByIds(String ids);

    /**
     * 删除币币订单信息
     * 
     * @param id 币币订单ID
     * @return 结果
     */
    public int deleteRcCurrencyOrderById(Long id);
}
