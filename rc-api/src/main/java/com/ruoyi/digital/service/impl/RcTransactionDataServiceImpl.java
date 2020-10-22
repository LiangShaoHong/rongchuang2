package com.ruoyi.digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.digital.mapper.RcTransactionDataMapper;
import com.ruoyi.digital.domain.RcTransactionData;
import com.ruoyi.digital.service.IRcTransactionDataService;
import com.ruoyi.common.core.text.Convert;

/**
 * 实时各交易所币种交易数据Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Service
public class RcTransactionDataServiceImpl implements IRcTransactionDataService 
{
    @Autowired
    private RcTransactionDataMapper rcTransactionDataMapper;

    /**
     * 查询实时各交易所币种交易数据
     * 
     * @param id 实时各交易所币种交易数据ID
     * @return 实时各交易所币种交易数据
     */
    @Override
    public RcTransactionData selectRcTransactionDataById(Long id)
    {
        return rcTransactionDataMapper.selectRcTransactionDataById(id);
    }

    /**
     * 查询实时各交易所币种交易数据列表
     * 
     * @param rcTransactionData 实时各交易所币种交易数据
     * @return 实时各交易所币种交易数据
     */
    @Override
    public List<RcTransactionData> selectRcTransactionDataList(RcTransactionData rcTransactionData)
    {
        return rcTransactionDataMapper.selectRcTransactionDataList(rcTransactionData);
    }

    /**
     * 新增实时各交易所币种交易数据
     * 
     * @param rcTransactionData 实时各交易所币种交易数据
     * @return 结果
     */
    @Override
    public int insertRcTransactionData(RcTransactionData rcTransactionData)
    {
        return rcTransactionDataMapper.insertRcTransactionData(rcTransactionData);
    }

    /**
     * 修改实时各交易所币种交易数据
     * 
     * @param rcTransactionData 实时各交易所币种交易数据
     * @return 结果
     */
    @Override
    public int updateRcTransactionData(RcTransactionData rcTransactionData)
    {
        return rcTransactionDataMapper.updateRcTransactionData(rcTransactionData);
    }

    /**
     * 删除实时各交易所币种交易数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcTransactionDataByIds(String ids)
    {
        return rcTransactionDataMapper.deleteRcTransactionDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除实时各交易所币种交易数据信息
     * 
     * @param id 实时各交易所币种交易数据ID
     * @return 结果
     */
    @Override
    public int deleteRcTransactionDataById(Long id)
    {
        return rcTransactionDataMapper.deleteRcTransactionDataById(id);
    }
}
