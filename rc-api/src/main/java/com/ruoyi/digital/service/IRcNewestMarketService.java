package com.ruoyi.digital.service;

import java.util.List;
import com.ruoyi.digital.domain.RcNewestMarket;

/**
 * 最新上市Service接口
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
public interface IRcNewestMarketService 
{
    /**
     * 查询最新上市
     * 
     * @param id 最新上市ID
     * @return 最新上市
     */
    public RcNewestMarket selectRcNewestMarketById(Long id);

    /**
     * 查询最新上市列表
     * 
     * @param rcNewestMarket 最新上市
     * @return 最新上市集合
     */
    public List<RcNewestMarket> selectRcNewestMarketList(RcNewestMarket rcNewestMarket);

    /**
     * 新增最新上市
     * 
     * @param rcNewestMarket 最新上市
     * @return 结果
     */
    public int insertRcNewestMarket(RcNewestMarket rcNewestMarket);

    /**
     * 修改最新上市
     * 
     * @param rcNewestMarket 最新上市
     * @return 结果
     */
    public int updateRcNewestMarket(RcNewestMarket rcNewestMarket);

    /**
     * 批量删除最新上市
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcNewestMarketByIds(String ids);

    /**
     * 删除最新上市信息
     * 
     * @param id 最新上市ID
     * @return 结果
     */
    public int deleteRcNewestMarketById(Long id);
}
