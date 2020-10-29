package com.ruoyi.order.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.RcCurrencyOrderReleaseMapper;
import com.ruoyi.order.domain.RcCurrencyOrderRelease;
import com.ruoyi.order.service.IRcCurrencyOrderReleaseService;
import com.ruoyi.common.core.text.Convert;

/**
 * 币币订单发布Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
@Service
public class RcCurrencyOrderReleaseServiceImpl implements IRcCurrencyOrderReleaseService 
{
    @Autowired
    private RcCurrencyOrderReleaseMapper rcCurrencyOrderReleaseMapper;

    /**
     * 查询币币订单发布
     * 
     * @param id 币币订单发布ID
     * @return 币币订单发布
     */
    @Override
    public RcCurrencyOrderRelease selectRcCurrencyOrderReleaseById(Long id)
    {
        return rcCurrencyOrderReleaseMapper.selectRcCurrencyOrderReleaseById(id);
    }

    /**
     * 查询币币订单发布列表
     * 
     * @param rcCurrencyOrderRelease 币币订单发布
     * @return 币币订单发布
     */
    @Override
    public List<RcCurrencyOrderRelease> selectRcCurrencyOrderReleaseList(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        return rcCurrencyOrderReleaseMapper.selectRcCurrencyOrderReleaseList(rcCurrencyOrderRelease);
    }

    /**
     * 新增币币订单发布
     * 
     * @param rcCurrencyOrderRelease 币币订单发布
     * @return 结果
     */
    @Override
    public int insertRcCurrencyOrderRelease(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        return rcCurrencyOrderReleaseMapper.insertRcCurrencyOrderRelease(rcCurrencyOrderRelease);
    }

    /**
     * 修改币币订单发布
     * 
     * @param rcCurrencyOrderRelease 币币订单发布
     * @return 结果
     */
    @Override
    public int updateRcCurrencyOrderRelease(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        return rcCurrencyOrderReleaseMapper.updateRcCurrencyOrderRelease(rcCurrencyOrderRelease);
    }

    /**
     * 删除币币订单发布对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcCurrencyOrderReleaseByIds(String ids)
    {
        return rcCurrencyOrderReleaseMapper.deleteRcCurrencyOrderReleaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除币币订单发布信息
     * 
     * @param id 币币订单发布ID
     * @return 结果
     */
    @Override
    public int deleteRcCurrencyOrderReleaseById(Long id)
    {
        return rcCurrencyOrderReleaseMapper.deleteRcCurrencyOrderReleaseById(id);
    }
}
