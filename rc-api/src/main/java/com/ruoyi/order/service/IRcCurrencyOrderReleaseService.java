package com.ruoyi.order.service;

import java.util.List;
import com.ruoyi.order.domain.RcCurrencyOrderRelease;

/**
 * 币币订单发布Service接口
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
public interface IRcCurrencyOrderReleaseService 
{
    /**
     * 查询币币订单发布
     * 
     * @param id 币币订单发布ID
     * @return 币币订单发布
     */
    public RcCurrencyOrderRelease selectRcCurrencyOrderReleaseById(Long id);

    /**
     * 查询币币订单发布列表
     * 
     * @param rcCurrencyOrderRelease 币币订单发布
     * @return 币币订单发布集合
     */
    public List<RcCurrencyOrderRelease> selectRcCurrencyOrderReleaseList(RcCurrencyOrderRelease rcCurrencyOrderRelease);

    /**
     * 新增币币订单发布
     * 
     * @param rcCurrencyOrderRelease 币币订单发布
     * @return 结果
     */
    public int insertRcCurrencyOrderRelease(RcCurrencyOrderRelease rcCurrencyOrderRelease);

    /**
     * 修改币币订单发布
     * 
     * @param rcCurrencyOrderRelease 币币订单发布
     * @return 结果
     */
    public int updateRcCurrencyOrderRelease(RcCurrencyOrderRelease rcCurrencyOrderRelease);

    /**
     * 批量删除币币订单发布
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcCurrencyOrderReleaseByIds(String ids);

    /**
     * 删除币币订单发布信息
     * 
     * @param id 币币订单发布ID
     * @return 结果
     */
    public int deleteRcCurrencyOrderReleaseById(Long id);
}
