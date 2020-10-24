package com.ruoyi.home.service.impl;

import com.ruoyi.common.utils.ResultDto;
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
    public ResultDto getNoticeList(Integer pageNumber, Integer limit) {
        List<RcNoticeHome> profit = homeMapper.getNoticeList(pageNumber, limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getNoticeDetail(Integer id) {
        RcNoticeHome profit = homeMapper.getNoticeDetail(id);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getNewNotice() {
        RcNoticeHome profit = homeMapper.getNewNotice();
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getLunboList() {
        List<RcLunboHome> profit = homeMapper.getLunboList();
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getInfoList(Integer pageNumber, Integer limit) {
        List<RcInformationHome> profit = homeMapper.getInfoList(pageNumber,limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getHelpList(Integer pageNumber, Integer limit) {
        List<RcHelpHome> profit = homeMapper.getHelpList(pageNumber,limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getHelpDetail(Integer id) {
        RcHelpHome profit = homeMapper.getHelpDetail(id);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }
}
