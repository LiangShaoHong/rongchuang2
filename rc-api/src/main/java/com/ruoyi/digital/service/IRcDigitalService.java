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
    ResultDto selectRcExchangeRateList();

}
