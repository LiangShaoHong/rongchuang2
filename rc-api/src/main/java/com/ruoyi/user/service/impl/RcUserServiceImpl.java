package com.ruoyi.user.service.impl;

import java.util.List;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.security.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.RcUserMapper;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.service.IRcUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户注册Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-22
 */
@Service
public class RcUserServiceImpl implements IRcUserService 
{
    @Autowired
    private RcUserMapper rcUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 查询用户注册
     * 
     * @param id 用户注册ID
     * @return 用户注册
     */
    @Override
    public RcUser selectRcUserById(Long id)
    {
        return rcUserMapper.selectRcUserById(id);
    }

    @Override
    public JSONObject selectinvitation(String invitation) {
        return rcUserMapper.selectinvitation(invitation);
    }

    @Override
    public JSONObject selectmobile(String mobile) {
        return rcUserMapper.selectmobile(mobile);
    }

    @Override
    public RcUser selectaccount(String account, String pass) {
        RcUser user = rcUserMapper.selectaccount(account);
        //对账号密码进行校验
        if (user != null) {
            boolean verify = bCryptPasswordEncoder.matches(user.getInvitation() + pass, user.getPassword());
            if (verify) {
                return user;
            }
        }
        return null;
    }

    /**
     * 查询用户注册列表
     * 
     * @param rcUser 用户注册
     * @return 用户注册
     */
    @Override
    public List<RcUser> selectRcUserList(RcUser rcUser)
    {
        return rcUserMapper.selectRcUserList(rcUser);
    }

    /**
     * 新增用户注册
     * 
     * @param rcUser 用户注册
     * @return 结果
     */
    @Override
    public int insertRcUser(RcUser rcUser)
    {
        return rcUserMapper.insertRcUser(rcUser);
    }

    /**
     * 修改用户注册
     * 
     * @param rcUser 用户注册
     * @return 结果
     */
    @Override
    public int updateRcUser(RcUser rcUser)
    {
        return rcUserMapper.updateRcUser(rcUser);
    }

    /**
     * 删除用户注册对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcUserByIds(String ids)
    {
        return rcUserMapper.deleteRcUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户注册信息
     * 
     * @param id 用户注册ID
     * @return 结果
     */
    @Override
    public int deleteRcUserById(Long id)
    {
        return rcUserMapper.deleteRcUserById(id);
    }

    @Override
    public JSONObject selectreferralcode(String referralcode) {
        return rcUserMapper.selectreferralcode(referralcode);
    }
}
