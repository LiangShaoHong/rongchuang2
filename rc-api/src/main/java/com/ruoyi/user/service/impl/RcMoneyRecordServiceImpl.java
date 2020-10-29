package com.ruoyi.user.service.impl;

import java.util.List;
import com.ruoyi.user.domain.RcMoneyRecord;
import com.ruoyi.user.mapper.RcMoneyRecordMapper;
import com.ruoyi.user.service.IRcMoneyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户金额变化记录Service业务层处理
 *
 * @author xiaoyu
 * @date 2020-10-29
 */
@Service
public class RcMoneyRecordServiceImpl implements IRcMoneyRecordService
{
    @Autowired
    private RcMoneyRecordMapper rcMoneyRecordMapper;

    /**
     * 查询用户金额变化记录
     *
     * @param id 用户金额变化记录ID
     * @return 用户金额变化记录
     */
    @Override
    public RcMoneyRecord selectRcMoneyRecordById(Integer id)
    {
        return rcMoneyRecordMapper.selectRcMoneyRecordById(id);
    }

    /**
     * 查询用户金额变化记录列表
     *
     * @param rcMoneyRecord 用户金额变化记录
     * @return 用户金额变化记录
     */
    @Override
    public List<RcMoneyRecord> selectRcMoneyRecordList(RcMoneyRecord rcMoneyRecord)
    {
        return rcMoneyRecordMapper.selectRcMoneyRecordList(rcMoneyRecord);
    }

    /**
     * 新增用户金额变化记录
     *
     * @param rcMoneyRecord 用户金额变化记录
     * @return 结果
     */
    @Override
    public int insertRcMoneyRecord(RcMoneyRecord rcMoneyRecord)
    {
        return rcMoneyRecordMapper.insertRcMoneyRecord(rcMoneyRecord);
    }

    /**
     * 修改用户金额变化记录
     *
     * @param rcMoneyRecord 用户金额变化记录
     * @return 结果
     */
    @Override
    public int updateRcMoneyRecord(RcMoneyRecord rcMoneyRecord)
    {
        return rcMoneyRecordMapper.updateRcMoneyRecord(rcMoneyRecord);
    }

    /**
     * 删除用户金额变化记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcMoneyRecordByIds(String ids)
    {
        return rcMoneyRecordMapper.deleteRcMoneyRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户金额变化记录信息
     *
     * @param id 用户金额变化记录ID
     * @return 结果
     */
    @Override
    public int deleteRcMoneyRecordById(Integer id)
    {
        return rcMoneyRecordMapper.deleteRcMoneyRecordById(id);
    }
}