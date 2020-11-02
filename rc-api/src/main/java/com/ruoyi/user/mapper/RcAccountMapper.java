package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.RcAccount;

/**
 * 账户收款Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-02
 */
public interface RcAccountMapper 
{
    /**
     * 查询账户收款
     * 
     * @param id 账户收款ID
     * @return 账户收款
     */
    public RcAccount selectRcAccountById(Long id);

    /**
     * 查询账户收款列表
     * 
     * @param rcAccount 账户收款
     * @return 账户收款集合
     */
    public List<RcAccount> selectRcAccountList(RcAccount rcAccount);

    /**
     * 新增账户收款
     * 
     * @param rcAccount 账户收款
     * @return 结果
     */
    public int insertRcAccount(RcAccount rcAccount);

    /**
     * 修改账户收款
     * 
     * @param rcAccount 账户收款
     * @return 结果
     */
    public int updateRcAccount(RcAccount rcAccount);

    /**
     * 删除账户收款
     * 
     * @param id 账户收款ID
     * @return 结果
     */
    public int deleteRcAccountById(Long id);

    /**
     * 批量删除账户收款
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcAccountByIds(String[] ids);
}
