package com.ruoyi.rcLunbo.service;

import java.util.List;
import com.ruoyi.rcLunbo.domain.RcLunbo;

/**
 * 轮播图Service接口
 * 
 * @author xiaoxia
 * @date 2020-10-21
 */
public interface IRcLunboService 
{
    /**
     * 查询轮播图
     * 
     * @param id 轮播图ID
     * @return 轮播图
     */
    public RcLunbo selectRcLunboById(Long id);

    /**
     * 查询轮播图列表
     * 
     * @param rcLunbo 轮播图
     * @return 轮播图集合
     */
    public List<RcLunbo> selectRcLunboList(RcLunbo rcLunbo);

    /**
     * 新增轮播图
     * 
     * @param rcLunbo 轮播图
     * @return 结果
     */
    public int insertRcLunbo(RcLunbo rcLunbo);

    /**
     * 修改轮播图
     * 
     * @param rcLunbo 轮播图
     * @return 结果
     */
    public int updateRcLunbo(RcLunbo rcLunbo);

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcLunboByIds(String ids);

    /**
     * 删除轮播图信息
     * 
     * @param id 轮播图ID
     * @return 结果
     */
    public int deleteRcLunboById(Long id);
}
