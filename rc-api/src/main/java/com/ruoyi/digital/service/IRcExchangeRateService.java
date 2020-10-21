package com.ruoyi.digital.service;

import java.util.List;
import com.ruoyi.digital.domain.RcExchangeRate;

/**
 * 汇率Service接口
 * 
 * @author xiaoxia
 * @date 2020-10-21
 */
public interface IRcExchangeRateService 
{
    /**
     * 查询汇率
     * 
     * @param id 汇率ID
     * @return 汇率
     */
    public RcExchangeRate selectRcExchangeRateById(Long id);

    /**
     * 查询汇率列表
     * 
     * @param rcExchangeRate 汇率
     * @return 汇率集合
     */
    public List<RcExchangeRate> selectRcExchangeRateList(RcExchangeRate rcExchangeRate);

    /**
     * 新增汇率
     * 
     * @param rcExchangeRate 汇率
     * @return 结果
     */
    public int insertRcExchangeRate(RcExchangeRate rcExchangeRate);

    /**
     * 修改汇率
     * 
     * @param rcExchangeRate 汇率
     * @return 结果
     */
    public int updateRcExchangeRate(RcExchangeRate rcExchangeRate);

    /**
     * 批量删除汇率
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcExchangeRateByIds(String ids);

    /**
     * 删除汇率信息
     * 
     * @param id 汇率ID
     * @return 结果
     */
    public int deleteRcExchangeRateById(Long id);
}
