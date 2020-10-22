package com.ruoyi.rc.mapper;

import java.util.List;
import com.ruoyi.rc.domain.RcUser;

/**
 * 用户注册Mapper接口
 * 
 * @author ruoyi
 * @date 2020-10-22
 */
public interface RcUserMapper 
{
    /**
     * 查询用户注册
     * 
     * @param id 用户注册ID
     * @return 用户注册
     */
    public RcUser selectRcUserById(Long id);

    /**
     * 查询用户注册列表
     * 
     * @param rcUser 用户注册
     * @return 用户注册集合
     */
    public List<RcUser> selectRcUserList(RcUser rcUser);

    /**
     * 新增用户注册
     * 
     * @param rcUser 用户注册
     * @return 结果
     */
    public int insertRcUser(RcUser rcUser);

    /**
     * 修改用户注册
     * 
     * @param rcUser 用户注册
     * @return 结果
     */
    public int updateRcUser(RcUser rcUser);

    /**
     * 删除用户注册
     * 
     * @param id 用户注册ID
     * @return 结果
     */
    public int deleteRcUserById(Long id);

    /**
     * 批量删除用户注册
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcUserByIds(String[] ids);
}
