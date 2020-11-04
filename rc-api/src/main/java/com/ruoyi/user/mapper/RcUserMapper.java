package com.ruoyi.user.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.user.domain.RcUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * 通过邀请码找到会员
     * @param showId：邀请码
     * @param isDel：是否删除
     * @param isUse：是否禁用
     * @return
     */
    JSONObject getUserByShowId(@Param("showId") String showId, @Param("isDel") Integer isDel, @Param("isUse") Integer isUse);

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
    /**tring
     *查询邀请码是否被使用
     *
     * @param invitation 邀请码
     * @return 结果
     */
    /**tring
     *查询邀请码是否被使用
     *
     * @param invitation 邀请码
     * @return 结果
     */
    JSONObject selectinvitation(@Param("invitation") String invitation);
    /**tring
     *查询手机号是否被使用
     *
     * @param mobile 邀请码
     * @return 结果
     */
    JSONObject selectmobile(@Param("mobile") String mobile);
    /**tring
     *查询账号是否被使用
     * @param account 账号

     * @return 结果
     */
    RcUser selectaccount(@Param("account") String account);
    /**tring
     *查询账号是否被使用
     * @param account 账号
     * @param password 密码
     * @return 结果
     */
    RcUser selectverify(@Param("account") String account,@Param("password") String password);

    JSONObject getUserMoney(@Param("id") String userId,@Param("account") String account);

    int editUserMoneyWallet(@Param("userId")String userId, @Param("account")String account,@Param("money") BigDecimal money);

    JSONObject selectreferralcode(@Param("referralcode") String referralcode);

    @Select("SELECT a.amount_day as amountDay,a.code as code,a.name as name,a.fullname as fullname,a.symbol as symbol,a.logo as logo,a.change_percent as changePercent,a.turn_over as turnOver FROM rc_transaction_info AS a,rc_attention AS b WHERE a.code = b.coin_type and b.status=0 and b.user_id=#{id}")
    JSONObject selectAttention(Long id);
}
