package com.ruoyi.help.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.help.mapper.RcHelpMapper;
import com.ruoyi.help.domain.RcHelp;
import com.ruoyi.help.service.IRcHelpService;
import com.ruoyi.common.core.text.Convert;

/**
 * 帮助信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-21
 */
@Service
public class RcHelpServiceImpl implements IRcHelpService 
{
    @Autowired
    private RcHelpMapper rcHelpMapper;

    /**
     * 查询帮助信息
     * 
     * @param id 帮助信息ID
     * @return 帮助信息
     */
    @Override
    public RcHelp selectRcHelpById(Long id)
    {
        return rcHelpMapper.selectRcHelpById(id);
    }

    /**
     * 查询帮助信息列表
     * 
     * @param rcHelp 帮助信息
     * @return 帮助信息
     */
    @Override
    public List<RcHelp> selectRcHelpList(RcHelp rcHelp)
    {
        return rcHelpMapper.selectRcHelpList(rcHelp);
    }

    /**
     * 新增帮助信息
     * 
     * @param rcHelp 帮助信息
     * @return 结果
     */
    @Override
    public int insertRcHelp(RcHelp rcHelp)
    {
        rcHelp.setCreateTime(DateUtils.getNowDate());
        return rcHelpMapper.insertRcHelp(rcHelp);
    }

    /**
     * 修改帮助信息
     * 
     * @param rcHelp 帮助信息
     * @return 结果
     */
    @Override
    public int updateRcHelp(RcHelp rcHelp)
    {
        return rcHelpMapper.updateRcHelp(rcHelp);
    }

    /**
     * 删除帮助信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcHelpByIds(String ids)
    {
        return rcHelpMapper.deleteRcHelpByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除帮助信息信息
     * 
     * @param id 帮助信息ID
     * @return 结果
     */
    @Override
    public int deleteRcHelpById(Long id)
    {
        return rcHelpMapper.deleteRcHelpById(id);
    }
}
