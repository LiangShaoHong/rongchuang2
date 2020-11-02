package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.RcAuth;

/**
 * 用户认证Service接口
 * 
 * @author xiaoyu
 * @date 2020-10-30
 */
public interface IRcAuthService 
{
    /**
     * 查询用户认证
     * 
     * @param id 用户认证ID
     * @return 用户认证
     */
    public RcAuth selectRcAuthById(Long id);

    /**
     * 查询用户认证列表
     * 
     * @param rcAuth 用户认证
     * @return 用户认证集合
     */
    public List<RcAuth> selectRcAuthList(RcAuth rcAuth);

    /**
     * 新增用户认证
     * 
     * @param rcAuth 用户认证
     * @return 结果
     */
    public int insertRcAuth(RcAuth rcAuth);

    /**
     * 修改用户认证
     * 
     * @param rcAuth 用户认证
     * @return 结果
     */
    public int updateRcAuth(RcAuth rcAuth);

    /**
     * 批量删除用户认证
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcAuthByIds(String ids);

    /**
     * 删除用户认证信息
     * 
     * @param id 用户认证ID
     * @return 结果
     */
    public int deleteRcAuthById(Long id);
}
