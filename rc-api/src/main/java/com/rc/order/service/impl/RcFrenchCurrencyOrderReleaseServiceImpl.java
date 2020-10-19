package com.rc.order.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rc.order.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.rc.order.domain.RcFrenchCurrencyOrderRelease;
import com.rc.order.service.IRcFrenchCurrencyOrderReleaseService;
import com.ruoyi.common.core.text.Convert;

/**
 * 法币订单发布Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-19
 */
@Service
public class RcFrenchCurrencyOrderReleaseServiceImpl implements IRcFrenchCurrencyOrderReleaseService 
{
    @Autowired
    private RcFrenchCurrencyOrderReleaseMapper rcFrenchCurrencyOrderReleaseMapper;

    /**
     * 查询法币订单发布
     * 
     * @param id 法币订单发布ID
     * @return 法币订单发布
     */
    @Override
    public RcFrenchCurrencyOrderRelease selectRcFrenchCurrencyOrderReleaseById(Long id)
    {
        return rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseById(id);
    }

    /**
     * 查询法币订单发布列表
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 法币订单发布
     */
    @Override
    public List<RcFrenchCurrencyOrderRelease> selectRcFrenchCurrencyOrderReleaseList(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseList(rcFrenchCurrencyOrderRelease);
    }

    /**
     * 新增法币订单发布
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 结果
     */
    @Override
    public int insertRcFrenchCurrencyOrderRelease(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return rcFrenchCurrencyOrderReleaseMapper.insertRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease);
    }

    /**
     * 修改法币订单发布
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 结果
     */
    @Override
    public int updateRcFrenchCurrencyOrderRelease(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return rcFrenchCurrencyOrderReleaseMapper.updateRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease);
    }

    /**
     * 删除法币订单发布对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcFrenchCurrencyOrderReleaseByIds(String ids)
    {
        return rcFrenchCurrencyOrderReleaseMapper.deleteRcFrenchCurrencyOrderReleaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除法币订单发布信息
     * 
     * @param id 法币订单发布ID
     * @return 结果
     */
    @Override
    public int deleteRcFrenchCurrencyOrderReleaseById(Long id)
    {
        return rcFrenchCurrencyOrderReleaseMapper.deleteRcFrenchCurrencyOrderReleaseById(id);
    }
}
