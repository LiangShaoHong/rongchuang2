package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.RcAttention;

/**
 * 用户关注Service接口
 * 
 * @author xiaoyu
 * @date 2020-11-03
 */
public interface IRcAttentionService 
{
    /**
     * 查询用户关注
     * 
     * @param id 用户关注ID
     * @return 用户关注
     */
    public RcAttention selectRcAttentionById(Long id);

    /**
     * 查询用户关注列表
     * 
     * @param rcAttention 用户关注
     * @return 用户关注集合
     */
    public List<RcAttention> selectRcAttentionList(RcAttention rcAttention);

    /**
     * 新增用户关注
     * 
     * @param rcAttention 用户关注
     * @return 结果
     */
    public int insertRcAttention(RcAttention rcAttention);

    /**
     * 修改用户关注
     * 
     * @param rcAttention 用户关注
     * @return 结果
     */
    public int updateRcAttention(RcAttention rcAttention);

    /**
     * 批量删除用户关注
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcAttentionByIds(String ids);

    /**
     * 删除用户关注信息
     * 
     * @param id 用户关注ID
     * @return 结果
     */
    public int deleteRcAttentionById(Long id);

    public RcAttention selectByUser(String coin_type, Long user_id);
}
