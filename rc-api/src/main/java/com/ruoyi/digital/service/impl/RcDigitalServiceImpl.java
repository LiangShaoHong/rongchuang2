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
    public Result getMarketList(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<RcNewestMarketDigital> profit = rcDigitalMapper.getMarketList(pageNum, pageSize);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getClinchList(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<RcTransactionInfoDigital> profit = rcDigitalMapper.getClinchList(pageNum, pageSize);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getDataList(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<RcTransactionDataDigital> profit = rcDigitalMapper.getDataList(pageNum, pageSize);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getPlatformList(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<RcTransactionPlatformDigital> profit = rcDigitalMapper.getPlatformList(pageNum, pageSize);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getInfoByCode(String code) {
        RcTransactionInfoDigital profit = rcDigitalMapper.getInfoByCode(code);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getInfoByCodeData(String code, String type) {
        // 所有=all 24H=d 一周=w 3月=3m 今年=ydt 1年=y
        RcTransactionInfoDigital profit = new RcTransactionInfoDigital();
        switch (type){
            case "all":
                profit = rcDigitalMapper.getInfoByCodeDataAll(code);
                break;
            case "d":
                profit = rcDigitalMapper.getInfoByCodeD(code);
                break;
            case "w":
                profit = rcDigitalMapper.getInfoByCodeW(code);
                break;
            case "3m":
                profit = rcDigitalMapper.getInfoByCodeThreeM(code);
                break;
            case "ydt":
                profit = rcDigitalMapper.getInfoByCodeYdt(code);
                break;
            case "y":
                profit = rcDigitalMapper.getInfoByCodeY(code);
                break;
            default:
                break;
        }

        return Result.isOk().data(profit).msg("查询成功");
    }

}
