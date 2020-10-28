package com.ruoyi.home.service.impl;

import com.ruoyi.common.Result;
import com.ruoyi.home.domain.RcHelpHome;
import com.ruoyi.home.domain.RcInformationHome;
import com.ruoyi.home.domain.RcLunboHome;
import com.ruoyi.home.domain.RcNoticeHome;
import com.ruoyi.home.mapper.RcHomeMapper;
import com.ruoyi.home.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author xiaoxia
 */
@Service
public class RcHomeServiceImpl implements IHomeService {

    @Autowired
    private RcHomeMapper homeMapper;

    @Override
    public Result getNoticeList(Integer pageNumber, Integer limit) {
        List<RcNoticeHome> profit = homeMapper.getNoticeList(pageNumber, limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getNoticeDetail(Integer id) {
        RcNoticeHome profit = homeMapper.getNoticeDetail(id);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getNewNotice() {
        RcNoticeHome profit = homeMapper.getNewNotice();
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getLunboList() {
        List<RcLunboHome> profit = homeMapper.getLunboList();
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getInfoList(Integer pageNumber, Integer limit) {
        List<RcInformationHome> profit = homeMapper.getInfoList(pageNumber,limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getHelpList(Integer pageNumber, Integer limit) {
        List<RcHelpHome> profit = homeMapper.getHelpList(pageNumber,limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getHelpDetail(Integer id) {
        RcHelpHome profit = homeMapper.getHelpDetail(id);
        return Result.isOk().data(profit).msg("查询成功");
    }
}
