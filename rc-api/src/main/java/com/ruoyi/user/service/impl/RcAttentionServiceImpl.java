package com.ruoyi.user.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.RcAttentionMapper;
import com.ruoyi.user.domain.RcAttention;
import com.ruoyi.user.service.IRcAttentionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户关注Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-11-03
 */
@Service
public class RcAttentionServiceImpl implements IRcAttentionService 
{
    @Autowired
    private RcAttentionMapper rcAttentionMapper;

    /**
     * 查询用户关注
     * 
     * @param id 用户关注ID
     * @return 用户关注
     */
    @Override
    public RcAttention selectRcAttentionById(Long id)
    {
        return rcAttentionMapper.selectRcAttentionById(id);
    }

    /**
     * 查询用户关注列表
     * 
     * @param rcAttention 用户关注
     * @return 用户关注
     */
    @Override
    public List<RcAttention> selectRcAttentionList(RcAttention rcAttention)
    {
        return rcAttentionMapper.selectRcAttentionList(rcAttention);
    }

    /**
     * 新增用户关注
     * 
     * @param rcAttention 用户关注
     * @return 结果
     */
    @Override
    public int insertRcAttention(RcAttention rcAttention)
    {
        return rcAttentionMapper.insertRcAttention(rcAttention);
    }

    /**
     * 修改用户关注
     * 
     * @param rcAttention 用户关注
     * @return 结果
     */
    @Override
    public int updateRcAttention(RcAttention rcAttention)
    {
        return rcAttentionMapper.updateRcAttention(rcAttention);
    }

    /**
     * 删除用户关注对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcAttentionByIds(String ids)
    {
        return rcAttentionMapper.deleteRcAttentionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户关注信息
     * 
     * @param id 用户关注ID
     * @return 结果
     */
    @Override
    public int deleteRcAttentionById(Long id)
    {
        return rcAttentionMapper.deleteRcAttentionById(id);
    }

    @Override
    public RcAttention selectByUser( String coin_type, Long user_id) {
        return rcAttentionMapper.selectByUser(coin_type,user_id);
    }
}
