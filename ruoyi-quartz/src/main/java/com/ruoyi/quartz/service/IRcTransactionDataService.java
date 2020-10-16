package com.ruoyi.quartz.service;

import com.ruoyi.quartz.domain.RcTransactionData;

import java.util.List;

/**
 * 实时各交易所币种交易数据Service接口
 *
 * @author ruoyi
 * @date 2020-10-15
 */
public interface IRcTransactionDataService
{
    /**
     * 查询实时各交易所币种交易数据
     *
     * @param id 实时各交易所币种交易数据ID
     * @return 实时各交易所币种交易数据
     */
    public RcTransactionData selectRcTransactionDataById(Long id);

    /**
     * 查询实时各交易所币种交易数据列表
     *
     * @param rcTransactionData 实时各交易所币种交易数据
     * @return 实时各交易所币种交易数据集合
     */
    public List<RcTransactionData> selectRcTransactionDataList(RcTransactionData rcTransactionData);

    /**
     * 新增实时各交易所币种交易数据
     *
     * @param rcTransactionData 实时各交易所币种交易数据
     * @return 结果
     */
    public int insertRcTransactionData(RcTransactionData rcTransactionData);

    /**
     * 修改实时各交易所币种交易数据
     *
     * @param rcTransactionData 实时各交易所币种交易数据
     * @return 结果
     */
    public int updateRcTransactionData(RcTransactionData rcTransactionData);

    /**
     * 批量删除实时各交易所币种交易数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcTransactionDataByIds(String ids);

    /**
     * 删除实时各交易所币种交易数据信息
     *
     * @param id 实时各交易所币种交易数据ID
     * @return 结果
     */
    public int deleteRcTransactionDataById(Long id);
}