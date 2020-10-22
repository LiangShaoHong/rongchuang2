package com.ruoyi.info.service;

import java.util.List;
import com.ruoyi.info.domain.RcInformation;

/**
 * 系统信息Service接口
 * 
 * @author ruoyi
 * @date 2020-10-21
 */
public interface IRcInformationService 
{
    /**
     * 查询系统信息
     * 
     * @param id 系统信息ID
     * @return 系统信息
     */
    public RcInformation selectRcInformationById(Long id);

    /**
     * 查询系统信息列表
     * 
     * @param rcInformation 系统信息
     * @return 系统信息集合
     */
    public List<RcInformation> selectRcInformationList(RcInformation rcInformation);

    /**
     * 新增系统信息
     * 
     * @param rcInformation 系统信息
     * @return 结果
     */
    public int insertRcInformation(RcInformation rcInformation);

    /**
     * 修改系统信息
     * 
     * @param rcInformation 系统信息
     * @return 结果
     */
    public int updateRcInformation(RcInformation rcInformation);

    /**
     * 批量删除系统信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcInformationByIds(String ids);

    /**
     * 删除系统信息信息
     * 
     * @param id 系统信息ID
     * @return 结果
     */
    public int deleteRcInformationById(Long id);
}
