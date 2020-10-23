package com.ruoyi.digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.digital.mapper.RcNewestMarketMapper;
import com.ruoyi.digital.domain.RcNewestMarket;
import com.ruoyi.digital.service.IRcNewestMarketService;
import com.ruoyi.common.core.text.Convert;

/**
 * 最新上市Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
@Service
public class RcNewestMarketServiceImpl implements IRcNewestMarketService 
{
    @Autowired
    private RcNewestMarketMapper rcNewestMarketMapper;

    /**
     * 查询最新上市
     * 
     * @param id 最新上市ID
     * @return 最新上市
     */
    @Override
    public RcNewestMarket selectRcNewestMarketById(Long id)
    {
        return rcNewestMarketMapper.selectRcNewestMarketById(id);
    }

    /**
     * 查询最新上市列表
     * 
     * @param rcNewestMarket 最新上市
     * @return 最新上市
     */
    @Override
    public List<RcNewestMarket> selectRcNewestMarketList(RcNewestMarket rcNewestMarket)
    {
        return rcNewestMarketMapper.selectRcNewestMarketList(rcNewestMarket);
    }

    /**
     * 新增最新上市
     * 
     * @param rcNewestMarket 最新上市
     * @return 结果
     */
    @Override
    public int insertRcNewestMarket(RcNewestMarket rcNewestMarket)
    {
        return rcNewestMarketMapper.insertRcNewestMarket(rcNewestMarket);
    }

    /**
     * 修改最新上市
     * 
     * @param rcNewestMarket 最新上市
     * @return 结果
     */
    @Override
    public int updateRcNewestMarket(RcNewestMarket rcNewestMarket)
    {
        return rcNewestMarketMapper.updateRcNewestMarket(rcNewestMarket);
    }

    /**
     * 删除最新上市对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcNewestMarketByIds(String ids)
    {
        return rcNewestMarketMapper.deleteRcNewestMarketByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除最新上市信息
     * 
     * @param id 最新上市ID
     * @return 结果
     */
    @Override
    public int deleteRcNewestMarketById(Long id)
    {
        return rcNewestMarketMapper.deleteRcNewestMarketById(id);
    }
}
