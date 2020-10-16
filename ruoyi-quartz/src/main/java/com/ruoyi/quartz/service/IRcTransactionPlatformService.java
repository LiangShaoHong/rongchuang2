package com.ruoyi.quartz.service;

import java.util.List;
import com.ruoyi.quartz.domain.RcTransactionPlatform;

/**
 * 交易所信息Service接口
 * 
 * @author ruoyi
 * @date 2020-10-16
 */
public interface IRcTransactionPlatformService 
{
    /**
     * 查询交易所信息
     * 
     * @param id 交易所信息ID
     * @return 交易所信息
     */
    public RcTransactionPlatform selectRcTransactionPlatformById(Long id);

    public RcTransactionPlatform selectRcTransactionPlatformByCoinId(String coinId);

    /**
     * 查询交易所信息列表
     * 
     * @param rcTransactionPlatform 交易所信息
     * @return 交易所信息集合
     */
    public List<RcTransactionPlatform> selectRcTransactionPlatformList(RcTransactionPlatform rcTransactionPlatform);

    /**
     * 新增交易所信息
     * 
     * @param rcTransactionPlatform 交易所信息
     * @return 结果
     */
    public int insertRcTransactionPlatform(RcTransactionPlatform rcTransactionPlatform);

    /**
     * 修改交易所信息
     * 
     * @param rcTransactionPlatform 交易所信息
     * @return 结果
     */
    public int updateRcTransactionPlatform(RcTransactionPlatform rcTransactionPlatform);

    public int updateRcTransactionPlatformByCoinId(RcTransactionPlatform rcTransactionPlatform);
    /**
     * 批量删除交易所信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcTransactionPlatformByIds(String ids);

    /**
     * 删除交易所信息信息
     * 
     * @param id 交易所信息ID
     * @return 结果
     */
    public int deleteRcTransactionPlatformById(Long id);
}
