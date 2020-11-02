package com.ruoyi.user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.RcAuthMapper;
import com.ruoyi.user.domain.RcAuth;
import com.ruoyi.user.service.IRcAuthService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户认证Service业务层处理
 * 
 * @author xiaoyu
 * @date 2020-10-30
 */
@Service
public class RcAuthServiceImpl implements IRcAuthService 
{
    @Autowired
    private RcAuthMapper rcAuthMapper;

    /**
     * 查询用户认证
     * 
     * @param id 用户认证ID
     * @return 用户认证
     */
    @Override
    public RcAuth selectRcAuthById(Long id)
    {
        return rcAuthMapper.selectRcAuthById(id);
    }

    /**
     * 查询用户认证列表
     * 
     * @param rcAuth 用户认证
     * @return 用户认证
     */
    @Override
    public List<RcAuth> selectRcAuthList(RcAuth rcAuth)
    {
        return rcAuthMapper.selectRcAuthList(rcAuth);
    }

    /**
     * 新增用户认证
     * 
     * @param rcAuth 用户认证
     * @return 结果
     */
    @Override
    public int insertRcAuth(RcAuth rcAuth)
    {
        return rcAuthMapper.insertRcAuth(rcAuth);
    }

    /**
     * 修改用户认证
     * 
     * @param rcAuth 用户认证
     * @return 结果
     */
    @Override
    public int updateRcAuth(RcAuth rcAuth)
    {
        return rcAuthMapper.updateRcAuth(rcAuth);
    }

    /**
     * 删除用户认证对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcAuthByIds(String ids)
    {
        return rcAuthMapper.deleteRcAuthByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户认证信息
     * 
     * @param id 用户认证ID
     * @return 结果
     */
    @Override
    public int deleteRcAuthById(Long id)
    {
        return rcAuthMapper.deleteRcAuthById(id);
    }
}
