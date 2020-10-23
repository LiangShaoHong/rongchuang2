package com.ruoyi.home.service.impl;

import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.home.mapper.RcHomeMapper;
import com.ruoyi.home.service.IHomeService;
import com.ruoyi.order.domain.Profit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaoxia
 */
@Service
public class RcHomeServiceImpl implements IHomeService {

    @Autowired
    private RcHomeMapper homeMapper;

    @Override
    public ResultDto getNoticeList(Integer pageNum, Integer pageSize) {
        Profit profit = homeMapper.getNoticeList(pageNum, pageSize);
        ResultDto resultDto = new ResultDto(1, "查询成功", profit);
        return resultDto;
    }

    @Override
    public ResultDto getNoticeDetail(Integer id) {
        Profit profit = homeMapper.getNoticeDetail(id);
        ResultDto resultDto = new ResultDto(1, "查询成功", profit);
        return resultDto;
    }

    @Override
    public ResultDto getNewNotice() {
        Profit profit = homeMapper.getNewNotice();
        ResultDto resultDto = new ResultDto(1, "查询成功", profit);
        return resultDto;
    }
}
