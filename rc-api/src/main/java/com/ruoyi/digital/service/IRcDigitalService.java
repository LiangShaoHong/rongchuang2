package com.ruoyi.digital.service;

import com.ruoyi.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xiaoxia
 */
@Api("首页数据展示接口")
public interface IRcDigitalService {

    @ApiOperation("获取汇率接口")
    Result getRateList();

    @ApiOperation("实时各交易所币种交易数据")
    Result getMarketList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询首页成交榜额列表接口")
    Result getClinchList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询首页市值行情列表接口")
    Result getDataList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询首页交易所列表接口")
    Result getPlatformList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询币种详情接口")
    Result getInfoByCode(String code);

    @ApiOperation("查询币种详情所有接口")
    Result getInfoByCodeData(String code, String type);

}
