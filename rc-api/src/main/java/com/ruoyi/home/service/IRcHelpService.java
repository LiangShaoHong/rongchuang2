package com.ruoyi.home.service;

import java.util.List;
import com.ruoyi.home.domain.RcHelp;

/**
 * 帮助信息Service接口
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
public interface IRcHelpService 
{
    /**
     * 查询帮助信息
     * 
     * @param id 帮助信息ID
     * @return 帮助信息
     */
    public RcHelp selectRcHelpById(Long id);

    /**
     * 查询帮助信息列表
     * 
     * @param rcHelp 帮助信息
     * @return 帮助信息集合
     */
    public List<RcHelp> selectRcHelpList(RcHelp rcHelp);

    /**
     * 新增帮助信息
     * 
     * @param rcHelp 帮助信息
     * @return 结果
     */
    public int insertRcHelp(RcHelp rcHelp);

    /**
     * 修改帮助信息
     * 
     * @param rcHelp 帮助信息
     * @return 结果
     */
    public int updateRcHelp(RcHelp rcHelp);

    /**
     * 批量删除帮助信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcHelpByIds(String ids);

    /**
     * 删除帮助信息信息
     * 
     * @param id 帮助信息ID
     * @return 结果
     */
    public int deleteRcHelpById(Long id);
}
