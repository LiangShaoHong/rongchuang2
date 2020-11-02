package com.ruoyi.digital.service.impl;

import com.ruoyi.common.Result;
import com.ruoyi.digital.domain.*;
import com.ruoyi.digital.mapper.RcDigitalMapper;
import com.ruoyi.digital.service.IRcDigitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
    public Result getMarketList(Integer pageNum, Integer pageSize, Integer byName, Integer byType) {
        pageNum = (pageNum - 1) * pageSize;
        // 1-市值 2-最新价 3-24H涨幅
        // 1-升序 2-降序
        String pageName = " ";
        String pageType = " DESC ";
        switch (byName){
            case 1:
                pageName = " price ";
                break;
            case 2:
                pageName = " change_percent ";
                break;
            default:
                pageName = " price ";
                break;
        }
        if(byType == 2){
            pageType = " ASC ";
        }
        List<RcNewestMarketDigital> profit = rcDigitalMapper.getMarketList(pageNum, pageSize, pageName, pageType);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getClinchList(Integer pageNum, Integer pageSize, Integer byName, Integer byType) {
        pageNum = (pageNum - 1) * pageSize;
        // 1-市值 2-最新价 3-24H涨幅
        // 1-升序 2-降序

        String pageType = " DESC ";
        if(byType == 2){
            pageType = " ASC ";
        }
        List<RcTransactionInfoDigital> profit = rcDigitalMapper.getClinchList(pageNum, pageSize, pageType);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getDataList(Integer pageNum, Integer pageSize, Integer byName, Integer byType) {
        pageNum = (pageNum - 1) * pageSize;
        // 1-市值 2-最新价 3-24H涨幅
        // 1-升序 2-降序
        String pageName = " ";
        String pageType = " DESC ";
        switch (byName){
            case 1:
                pageName = " market_value_usd ";
                break;
            case 2:
                pageName = " current_price_usd ";
                break;
            case 3:
                pageName = " change_percent ";
                break;
            default:
                pageName = " rank ";
                break;
        }
        if(byType == 2){
            pageType = " ASC ";
        }
        List<RcTransactionDataDigital> profit = rcDigitalMapper.getDataList(pageNum, pageSize, pageName, pageType);

        // 正序
        List<RcTransactionDataDigital> newList = profit.stream()
                .sorted(Comparator.comparing(RcTransactionDataDigital::getMarketValueUsd))
                .collect(Collectors.toList());
        // 倒序
        List<RcTransactionDataDigital> newListTwo = profit.stream()
                .sorted(Comparator.comparing(RcTransactionDataDigital::getMarketValueUsd).reversed())
                .collect(Collectors.toList());

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
