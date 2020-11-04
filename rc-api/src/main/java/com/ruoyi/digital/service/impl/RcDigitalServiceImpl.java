package com.ruoyi.digital.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.ruoyi.common.Result;
import com.ruoyi.digital.domain.*;
import com.ruoyi.digital.mapper.RcDigitalMapper;
import com.ruoyi.digital.service.IRcDigitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
        // 1-全球指数 2-24H涨幅
        // 1-升序 2-降序
        String pageName = " ";
        String pageType = " ASC ";
        switch (byName){
            case 2:
                pageName = " change_percent ";
                break;
            default:
                pageName = " price ";
                break;
        }
        if(byType == 2){
            pageType = " DESC ";
        }
        List<RcNewestMarketDigital> profit = rcDigitalMapper.getMarketList(pageNum, pageSize, pageName, pageType);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getClinchList(Integer pageNum, Integer pageSize, Integer byName, Integer byType) {
        pageNum = (pageNum - 1) * pageSize;
        // 1-24H涨幅
        // 1-升序 2-降序
        String pageName = " ";
        String pageType = " ASC ";
        switch (byName){
            default:
                pageName = " amount_day ";
                break;
        }
        if(byType == 2){
            pageType = " DESC ";
        }
        List<RcTransactionInfoDigital> profit = rcDigitalMapper.getClinchList(pageNum, pageSize, pageName, pageType);
        return Result.isOk().data(profit).msg("查询成功");
    }

    @Override
    public Result getDataList(Integer pageNum, Integer pageSize, Integer byName, Integer byType) {
        pageNum = (pageNum - 1) * pageSize;
        String pageName = " ";
        String pageType = " ASC ";
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
            pageType = " DESC ";
        }
        List<RcTransactionDataDigital> profitList = rcDigitalMapper.getDataList(pageNum, pageSize,pageName,pageType);
        String rate = "usd";
        switch (rate){
            case "cny":
                for (RcTransactionDataDigital doman : profitList){
                    doman.setMarketValueUsd(doman.getMarketValueUsd().multiply(new BigDecimal(2222433)));
                }
                break;
            default:
                break;
        }

        // 1-市值 2-最新价 3-24H涨幅
        // 1-升序 2-降序
//        List<RcTransactionDataDigital> profit = profitList;
//        if(byName == 1 && byType == 1){
//            profit = profitList.stream()
//                    .sorted(Comparator.comparing(RcTransactionDataDigital::getMarketValueUsd).reversed())
//                    .collect(Collectors.toList());
//        }else if(byName == 1 && byType == 2){
//            profit = profitList.stream()
//                    .sorted(Comparator.comparing(RcTransactionDataDigital::getMarketValueUsd))
//                    .collect(Collectors.toList());
//        }else if(byName == 2 && byType == 1){
//            profit = profitList.stream()
//                    .sorted(Comparator.comparing(RcTransactionDataDigital::getCurrentPriceUsd).reversed())
//                    .collect(Collectors.toList());
//        }else if(byName == 2 && byType == 2){
//            profit = profitList.stream()
//                    .sorted(Comparator.comparing(RcTransactionDataDigital::getCurrentPriceUsd))
//                    .collect(Collectors.toList());
//        }else if(byName == 3 && byType == 1){
//            profit = profitList.stream()
//                    .sorted(Comparator.comparing(RcTransactionDataDigital::getChangePercent).reversed())
//                    .collect(Collectors.toList());
//        }else if(byName == 3 && byType == 2){
//            profit = profitList.stream()
//                    .sorted(Comparator.comparing(RcTransactionDataDigital::getChangePercent))
//                    .collect(Collectors.toList());
//        }
        return Result.isOk().data(profitList).msg("查询成功");
    }

    @Override
    public Result getPlatformList(Integer pageNum, Integer pageSize, Integer byName, Integer byType) {
        pageNum = (pageNum - 1) * pageSize;
        String pageName = " ";
        String pageType = " ASC ";
        switch (byName){
            case 1:
                pageName = " volumn ";
                break;
            case 2:
                pageName = " assets_usd ";
                break;
            default:
                pageName = " rank ";
                break;
        }
        if(byType == 2){
            pageType = " DESC ";
        }
        List<RcTransactionPlatformDigital> profit = rcDigitalMapper.getPlatformList(pageNum, pageSize,pageName,pageType);
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
        JSONArray jsonArray = new JSONArray();
        switch (type){
            case "all":
                profit = rcDigitalMapper.getInfoByCodeDataAll(code);
                if(profit.getInfo_all() != null){
                    jsonArray = JSONArray.fromObject(Arrays.toString(StringUtils.delimitedListToStringArray(profit.getInfo_all(), ",")));
                }
                break;
            case "d":
                profit = rcDigitalMapper.getInfoByCodeD(code);
                if(profit.getInfo_d() != null) {
                    jsonArray = JSONArray.fromObject(Arrays.toString(StringUtils.delimitedListToStringArray(profit.getInfo_d(), ",")));
                }
                break;
            case "w":
                profit = rcDigitalMapper.getInfoByCodeW(code);
                if(profit.getInfo_w() != null) {
                    jsonArray = JSONArray.fromObject(Arrays.toString(StringUtils.delimitedListToStringArray(profit.getInfo_w(), ",")));
                }
                break;
            case "3m":
                profit = rcDigitalMapper.getInfoByCodeThreeM(code);
                if(profit.getInfo_3m() != null) {
                    jsonArray = JSONArray.fromObject(Arrays.toString(StringUtils.delimitedListToStringArray(profit.getInfo_3m(), ",")));
                }
                break;
            case "ydt":
                profit = rcDigitalMapper.getInfoByCodeYdt(code);
                if(profit.getInfo_ydt() != null) {
                    jsonArray = JSONArray.fromObject(Arrays.toString(StringUtils.delimitedListToStringArray(profit.getInfo_ydt(), ",")));
                }
                break;
            case "y":
                profit = rcDigitalMapper.getInfoByCodeY(code);
                if(profit.getInfo_y() != null) {
                    jsonArray = JSONArray.fromObject(Arrays.toString(StringUtils.delimitedListToStringArray(profit.getInfo_y(), ",")));
                }
                break;
            default:
                break;
        }
        if(jsonArray.size() != 0){
            Object[] objs = jsonArray.toArray();
            List timeList = new ArrayList<>();
            List dataList = new ArrayList<>();
            for (Object object : objs) {
                String jsonObj = object.toString();
                timeList.add(jsonObj.split(",")[0].replace("[",""));
                dataList.add(jsonObj.split(",")[1]);
            }
            profit.setDataList(dataList);
            profit.setTimeList(timeList);
        }
        return Result.isOk().data(profit).msg("查询成功");
    }

}
