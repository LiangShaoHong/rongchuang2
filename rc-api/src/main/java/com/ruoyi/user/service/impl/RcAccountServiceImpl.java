package com.ruoyi.user.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.RcAccountMapper;
import com.ruoyi.user.domain.RcAccount;
import com.ruoyi.user.service.IRcAccountService;
import com.ruoyi.common.core.text.Convert;

/**
 * 账户收款Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-02
 */
@Service
public class RcAccountServiceImpl implements IRcAccountService 
{
    @Autowired
    private RcAccountMapper rcAccountMapper;

    /**
     * 查询账户收款
     * 
     * @param id 账户收款ID
     * @return 账户收款
     */
    @Override
    public RcAccount selectRcAccountById(Long id)
    {
        return rcAccountMapper.selectRcAccountById(id);
    }

    /**
     * 查询账户收款列表
     * 
     * @param rcAccount 账户收款
     * @return 账户收款
     */
    @Override
    public List<RcAccount> selectRcAccountList(RcAccount rcAccount)
    {
        return rcAccountMapper.selectRcAccountList(rcAccount);
    }

    /**
     * 新增账户收款
     * 
     * @param rcAccount 账户收款
     * @return 结果
     */
    @Override
    public int insertRcAccount(RcAccount rcAccount)
    {
        rcAccount.setCreateTime(DateUtils.getNowDate());
        return rcAccountMapper.insertRcAccount(rcAccount);
    }

    /**
     * 修改账户收款
     * 
     * @param rcAccount 账户收款
     * @return 结果
     */
    @Override
    public int updateRcAccount(RcAccount rcAccount)
    {
        rcAccount.setUpdateTime(DateUtils.getNowDate());
        return rcAccountMapper.updateRcAccount(rcAccount);
    }

    /**
     * 删除账户收款对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcAccountByIds(String ids)
    {
        return rcAccountMapper.deleteRcAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除账户收款信息
     * 
     * @param id 账户收款ID
     * @return 结果
     */
    @Override
    public int deleteRcAccountById(Long id)
    {
        return rcAccountMapper.deleteRcAccountById(id);
    }
}
