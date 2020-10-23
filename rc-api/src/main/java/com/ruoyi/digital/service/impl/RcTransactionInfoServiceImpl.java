package com.ruoyi.digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.digital.mapper.RcTransactionInfoMapper;
import com.ruoyi.digital.domain.RcTransactionInfo;
import com.ruoyi.digital.service.IRcTransactionInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 数据列表Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
@Service
public class RcTransactionInfoServiceImpl implements IRcTransactionInfoService 
{
    @Autowired
    private RcTransactionInfoMapper rcTransactionInfoMapper;

    /**
     * 查询数据列表
     * 
     * @param id 数据列表ID
     * @return 数据列表
     */
    @Override
    public RcTransactionInfo selectRcTransactionInfoById(Long id)
    {
        return rcTransactionInfoMapper.selectRcTransactionInfoById(id);
    }

    /**
     * 查询数据列表列表
     * 
     * @param rcTransactionInfo 数据列表
     * @return 数据列表
     */
    @Override
    public List<RcTransactionInfo> selectRcTransactionInfoList(RcTransactionInfo rcTransactionInfo)
    {
        return rcTransactionInfoMapper.selectRcTransactionInfoList(rcTransactionInfo);
    }

    /**
     * 新增数据列表
     * 
     * @param rcTransactionInfo 数据列表
     * @return 结果
     */
    @Override
    public int insertRcTransactionInfo(RcTransactionInfo rcTransactionInfo)
    {
        return rcTransactionInfoMapper.insertRcTransactionInfo(rcTransactionInfo);
    }

    /**
     * 修改数据列表
     * 
     * @param rcTransactionInfo 数据列表
     * @return 结果
     */
    @Override
    public int updateRcTransactionInfo(RcTransactionInfo rcTransactionInfo)
    {
        return rcTransactionInfoMapper.updateRcTransactionInfo(rcTransactionInfo);
    }

    /**
     * 删除数据列表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcTransactionInfoByIds(String ids)
    {
        return rcTransactionInfoMapper.deleteRcTransactionInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除数据列表信息
     * 
     * @param id 数据列表ID
     * @return 结果
     */
    @Override
    public int deleteRcTransactionInfoById(Long id)
    {
        return rcTransactionInfoMapper.deleteRcTransactionInfoById(id);
    }
}
