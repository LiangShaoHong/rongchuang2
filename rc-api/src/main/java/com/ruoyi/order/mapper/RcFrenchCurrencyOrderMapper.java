package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;

/**
 * 法币订单Mapper接口
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
public interface RcFrenchCurrencyOrderMapper 
{
    /**
     * 查询法币订单
     * 
     * @param id 法币订单ID
     * @return 法币订单
     */
    public RcFrenchCurrencyOrder selectRcFrenchCurrencyOrderById(Long id);

    /**
     * 查询法币订单列表
     * 
     * @param rcFrenchCurrencyOrder 法币订单
     * @return 法币订单集合
     */
    public List<RcFrenchCurrencyOrder> selectRcFrenchCurrencyOrderList(RcFrenchCurrencyOrder rcFrenchCurrencyOrder);

    /**
     * 新增法币订单
     * 
     * @param rcFrenchCurrencyOrder 法币订单
     * @return 结果
     */
    public int insertRcFrenchCurrencyOrder(RcFrenchCurrencyOrder rcFrenchCurrencyOrder);

    /**
     * 修改法币订单
     * 
     * @param rcFrenchCurrencyOrder 法币订单
     * @return 结果
     */
    public int updateRcFrenchCurrencyOrder(RcFrenchCurrencyOrder rcFrenchCurrencyOrder);

    /**
     * 删除法币订单
     * 
     * @param id 法币订单ID
     * @return 结果
     */
    public int deleteRcFrenchCurrencyOrderById(Long id);

    /**
     * 批量删除法币订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcFrenchCurrencyOrderByIds(String[] ids);
}
