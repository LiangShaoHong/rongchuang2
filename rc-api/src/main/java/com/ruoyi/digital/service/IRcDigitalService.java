package com.ruoyi.digital.service;

import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xiaoxia
 */
@Api("首页数据展示接口")
public interface IRcDigitalService {

    @ApiOperation("获取汇率接口")
    ResultDto getRateList();

    @ApiOperation("实时各交易所币种交易数据")
    ResultDto getMarketList(Integer pageNumber, Integer limit);

    @ApiOperation("查询首页成交榜额列表接口")
    ResultDto getClinchList(Integer pageNumber, Integer limit);

    @ApiOperation("查询首页市值行情列表接口")
    ResultDto getDataList(Integer pageNumber, Integer limit);

    @ApiOperation("查询首页交易所列表接口")
    ResultDto getPlatformList(Integer pageNumber, Integer limit);

    @ApiOperation("查询币种详情接口")
    ResultDto getInfoByCode(String code);

}
