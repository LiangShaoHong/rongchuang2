package com.ruoyi.digital.service.impl;

import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.digital.domain.*;
import com.ruoyi.digital.mapper.RcDigitalMapper;
import com.ruoyi.digital.service.IRcDigitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author xiaoxia
 */
@Service
public class RcDigitalServiceImpl implements IRcDigitalService {

    @Autowired
    private RcDigitalMapper rcDigitalMapper;

    @Override
    public ResultDto getRateList() {
        List<RcExchangeRateDigital> profit = rcDigitalMapper.getRateList();
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getMarketList(Integer pageNumber, Integer limit) {
        List<RcNewestMarketDigital> profit = rcDigitalMapper.getMarketList(pageNumber, limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getClinchList(Integer pageNumber, Integer limit) {
        List<RcTransactionInfoDigital> profit = rcDigitalMapper.getClinchList(pageNumber, limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getDataList(Integer pageNumber, Integer limit) {
        List<RcTransactionDataDigital> profit = rcDigitalMapper.getDataList(pageNumber, limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getPlatformList(Integer pageNumber, Integer limit) {
        List<RcTransactionPlatformDigital> profit = rcDigitalMapper.getPlatformList(pageNumber, limit);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

    @Override
    public ResultDto getInfoByCode(String code) {
        RcTransactionInfoDigital profit = rcDigitalMapper.getInfoByCode(code);
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

}
