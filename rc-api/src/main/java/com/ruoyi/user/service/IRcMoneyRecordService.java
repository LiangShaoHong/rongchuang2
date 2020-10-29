package com.ruoyi.user.service;

import com.ruoyi.user.domain.RcMoneyRecord;
import java.util.List;

/**
 * 用户金额变化记录Service接口
 *
 * @author xiaoyu
 * @date 2020-10-29
 */
public interface IRcMoneyRecordService
{
    /**
     * 查询用户金额变化记录
     *
     * @param id 用户金额变化记录ID
     * @return 用户金额变化记录
     */
    public RcMoneyRecord selectRcMoneyRecordById(Integer id);

    /**
     * 查询用户金额变化记录列表
     *
     * @param rcMoneyRecord 用户金额变化记录
     * @return 用户金额变化记录集合
     */
    public List<RcMoneyRecord> selectRcMoneyRecordList(RcMoneyRecord rcMoneyRecord);

    /**
     * 新增用户金额变化记录
     *
     * @param rcMoneyRecord 用户金额变化记录
     * @return 结果
     */
    public int insertRcMoneyRecord(RcMoneyRecord rcMoneyRecord);

    /**
     * 修改用户金额变化记录
     *
     * @param rcMoneyRecord 用户金额变化记录
     * @return 结果
     */
    public int updateRcMoneyRecord(RcMoneyRecord rcMoneyRecord);

    /**
     * 批量删除用户金额变化记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcMoneyRecordByIds(String ids);

    /**
     * 删除用户金额变化记录信息
     *
     * @param id 用户金额变化记录ID
     * @return 结果
     */
    public int deleteRcMoneyRecordById(Integer id);
}