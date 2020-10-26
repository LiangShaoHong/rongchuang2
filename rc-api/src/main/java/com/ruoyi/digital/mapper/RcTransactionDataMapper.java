package com.ruoyi.digital.mapper;

import java.util.List;
import com.ruoyi.digital.domain.RcTransactionData;

/**
 * 实时各交易所币种交易数据Mapper接口
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
public interface RcTransactionDataMapper 
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
     * 删除实时各交易所币种交易数据
     * 
     * @param id 实时各交易所币种交易数据ID
     * @return 结果
     */
    public int deleteRcTransactionDataById(Long id);

    /**
     * 批量删除实时各交易所币种交易数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcTransactionDataByIds(String[] ids);

    public void insertRcTransactionDataList(List<RcTransactionData> listData);

    public void updateRcTransactionDataList(List<RcTransactionData> listData);
}
