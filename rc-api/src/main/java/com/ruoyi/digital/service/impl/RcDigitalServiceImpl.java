package com.ruoyi.digital.service.impl;

import com.ruoyi.common.Result;
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
    public Result getRateList() {
        List<RcExchangeRateDigital> profit = rcDigitalMapper.getRateList();
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getMarketList(Integer pageNumber, Integer limit) {
        List<RcNewestMarketDigital> profit = rcDigitalMapper.getMarketList(pageNumber, limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getClinchList(Integer pageNumber, Integer limit) {
        List<RcTransactionInfoDigital> profit = rcDigitalMapper.getClinchList(pageNumber, limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getDataList(Integer pageNumber, Integer limit) {
        List<RcTransactionDataDigital> profit = rcDigitalMapper.getDataList(pageNumber, limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getPlatformList(Integer pageNumber, Integer limit) {
        List<RcTransactionPlatformDigital> profit = rcDigitalMapper.getPlatformList(pageNumber, limit);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getInfoByCode(String code) {
        RcTransactionInfoDigital profit = rcDigitalMapper.getInfoByCode(code);
        return Result.isOk().data(profit).msg("查询成功");
    }

}
