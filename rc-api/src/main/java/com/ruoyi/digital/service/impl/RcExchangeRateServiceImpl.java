package com.ruoyi.digital.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.digital.mapper.RcExchangeRateMapper;
import com.ruoyi.digital.domain.RcExchangeRate;
import com.ruoyi.digital.service.IRcExchangeRateService;
import com.ruoyi.common.core.text.Convert;

/**
 * 汇率Service业务层处理
 * 
 * @author xiaoxia
 * @date 2020-10-21
 */
@Service
public class RcExchangeRateServiceImpl implements IRcExchangeRateService 
{
    @Autowired
    private RcExchangeRateMapper rcExchangeRateMapper;

    /**
     * 查询汇率
     * 
     * @param id 汇率ID
     * @return 汇率
     */
    @Override
    public RcExchangeRate selectRcExchangeRateById(Long id)
    {
        return rcExchangeRateMapper.selectRcExchangeRateById(id);
    }

    /**
     * 查询汇率列表
     * 
     * @param rcExchangeRate 汇率
     * @return 汇率
     */
    @Override
    public List<RcExchangeRate> selectRcExchangeRateList(RcExchangeRate rcExchangeRate)
    {
        return rcExchangeRateMapper.selectRcExchangeRateList(rcExchangeRate);
    }

    /**
     * 新增汇率
     * 
     * @param rcExchangeRate 汇率
     * @return 结果
     */
    @Override
    public int insertRcExchangeRate(RcExchangeRate rcExchangeRate)
    {
        return rcExchangeRateMapper.insertRcExchangeRate(rcExchangeRate);
    }

    /**
     * 修改汇率
     * 
     * @param rcExchangeRate 汇率
     * @return 结果
     */
    @Override
    public int updateRcExchangeRate(RcExchangeRate rcExchangeRate)
    {
        rcExchangeRate.setUpdateTime(DateUtils.getNowDate());
        return rcExchangeRateMapper.updateRcExchangeRate(rcExchangeRate);
    }

    /**
     * 删除汇率对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcExchangeRateByIds(String ids)
    {
        return rcExchangeRateMapper.deleteRcExchangeRateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除汇率信息
     * 
     * @param id 汇率ID
     * @return 结果
     */
    @Override
    public int deleteRcExchangeRateById(Long id)
    {
        return rcExchangeRateMapper.deleteRcExchangeRateById(id);
    }
}
