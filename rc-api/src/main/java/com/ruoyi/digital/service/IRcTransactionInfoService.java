package com.ruoyi.digital.service;

import java.util.List;
import com.ruoyi.digital.domain.RcTransactionInfo;

/**
 * 数据列表Service接口
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
public interface IRcTransactionInfoService 
{
    /**
     * 查询数据列表
     * 
     * @param id 数据列表ID
     * @return 数据列表
     */
    public RcTransactionInfo selectRcTransactionInfoById(Long id);

    /**
     * 查询数据列表列表
     * 
     * @param rcTransactionInfo 数据列表
     * @return 数据列表集合
     */
    public List<RcTransactionInfo> selectRcTransactionInfoList(RcTransactionInfo rcTransactionInfo);

    /**
     * 新增数据列表
     * 
     * @param rcTransactionInfo 数据列表
     * @return 结果
     */
    public int insertRcTransactionInfo(RcTransactionInfo rcTransactionInfo);

    /**
     * 修改数据列表
     * 
     * @param rcTransactionInfo 数据列表
     * @return 结果
     */
    public int updateRcTransactionInfo(RcTransactionInfo rcTransactionInfo);

    /**
     * 批量删除数据列表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcTransactionInfoByIds(String ids);

    /**
     * 删除数据列表信息
     * 
     * @param id 数据列表ID
     * @return 结果
     */
    public int deleteRcTransactionInfoById(Long id);
}
