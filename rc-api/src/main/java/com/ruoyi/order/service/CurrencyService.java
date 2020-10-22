package com.ruoyi.order.service;

import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 币币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("币币业务接口")
public interface CurrencyService {

    @ApiOperation("查询个人信息接口")
    public ResultDto getBbPerInformation(String X_Token);


    @ApiOperation("查询我的订单列表接口")
    public ResultDto getBbMyOrderList(String X_Token, PageDomain pageDomain);

    @ApiOperation("查询自动抢单状态接口")
    public ResultDto getBbAutomaticOrder(String X_Token);

    @ApiOperation("改变自动抢单状态接口")
    public ResultDto editBbAutomaticOrder(String X_Token, Boolean automatic);

    @ApiOperation("查询可选订单列表接口")
    public ResultDto getBbOptionalOrder(String X_Token, PageDomain pageDomain);

    @ApiOperation("查询历史记录接口")
    public ResultDto getBbHistorical(String X_Token, PageDomain pageDomain);


    @ApiOperation("查询历史记录详情接口")
    public ResultDto getBbDetails(String X_Token, String id);

    @ApiOperation("确定收款接口")

    public ResultDto bbConfirm(String X_Token, String id);

    @ApiOperation("抢订单接口")

    public ResultDto robBbOrder(String X_Token, String id);
}
