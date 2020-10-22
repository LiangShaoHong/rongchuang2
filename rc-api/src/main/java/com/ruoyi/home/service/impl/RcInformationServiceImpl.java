package com.ruoyi.home.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.home.mapper.RcInformationMapper;
import com.ruoyi.home.domain.RcInformation;
import com.ruoyi.home.service.IRcInformationService;
import com.ruoyi.common.core.text.Convert;

/**
 * 系统信息Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Service
public class RcInformationServiceImpl implements IRcInformationService 
{
    @Autowired
    private RcInformationMapper rcInformationMapper;

    /**
     * 查询系统信息
     * 
     * @param id 系统信息ID
     * @return 系统信息
     */
    @Override
    public RcInformation selectRcInformationById(Long id)
    {
        return rcInformationMapper.selectRcInformationById(id);
    }

    /**
     * 查询系统信息列表
     * 
     * @param rcInformation 系统信息
     * @return 系统信息
     */
    @Override
    public List<RcInformation> selectRcInformationList(RcInformation rcInformation)
    {
        return rcInformationMapper.selectRcInformationList(rcInformation);
    }

    /**
     * 新增系统信息
     * 
     * @param rcInformation 系统信息
     * @return 结果
     */
    @Override
    public int insertRcInformation(RcInformation rcInformation)
    {
        rcInformation.setCreateTime(DateUtils.getNowDate());
        return rcInformationMapper.insertRcInformation(rcInformation);
    }

    /**
     * 修改系统信息
     * 
     * @param rcInformation 系统信息
     * @return 结果
     */
    @Override
    public int updateRcInformation(RcInformation rcInformation)
    {
        return rcInformationMapper.updateRcInformation(rcInformation);
    }

    /**
     * 删除系统信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcInformationByIds(String ids)
    {
        return rcInformationMapper.deleteRcInformationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统信息信息
     * 
     * @param id 系统信息ID
     * @return 结果
     */
    @Override
    public int deleteRcInformationById(Long id)
    {
        return rcInformationMapper.deleteRcInformationById(id);
    }
}
