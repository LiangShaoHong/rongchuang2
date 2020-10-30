package com.ruoyi.user.service;

import java.util.List;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.user.domain.RcUser;
import org.apache.ibatis.annotations.Param;

/**
 * 用户注册Service接口
 * 
 * @author ruoyi
 * @date 2020-10-22
 */
public interface IRcUserService 
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
     * 批量删除用户注册
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcUserByIds(String ids);

    /**
     * 删除用户注册信息
     * 
     * @param id 用户注册ID
     * @return 结果
     */
    public int deleteRcUserById(Long id);

    /**
     * 查询推荐码是否被使用
     * @param invitation    推荐码
     * @return
     */
    JSONObject selectinvitation(String invitation);

    /**
     * 查询手机号是否被使用
     * @param mobile    手机号
     * @return
     */
    JSONObject selectmobile(String mobile);

    /**
     * 登陆
     * @param account   账号
     * @param pass      密码
     * @return
     */
    RcUser selectaccount(String account, String pass);

    JSONObject selectreferralcode(String referralcode);
}
