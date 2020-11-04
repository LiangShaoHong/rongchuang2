package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.RcAttention;
import org.apache.ibatis.annotations.Param;

/**
 * 用户关注Mapper接口
 * 
 * @author xiaoyu
 * @date 2020-11-03
 */
public interface RcAttentionMapper 
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
     * 删除用户关注
     * 
     * @param id 用户关注ID
     * @return 结果
     */
    public int deleteRcAttentionById(Long id);

    /**
     * 批量删除用户关注
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcAttentionByIds(String[] ids);

    public RcAttention selectByUser(@Param("coin_type") String coin_type, @Param("user_id") Long user_id);
}
