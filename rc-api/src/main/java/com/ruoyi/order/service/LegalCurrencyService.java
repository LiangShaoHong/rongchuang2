package com.ruoyi.order.service;

import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 法币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("法币业务接口")
public interface LegalCurrencyService {

    @ApiOperation("查询个人信息接口")
    public ResultDto getFbPerInformation(String X_Token);


    @ApiOperation("查询我的订单列表接口")
    public ResultDto getFbMyOrderList(String X_Token, Integer pageNum, Integer pageSize);

    @ApiOperation("查询自动抢单状态接口")
    public ResultDto getFbAutomaticOrder(String X_Token);

    @ApiOperation("改变自动抢单状态接口")
    public ResultDto editFbAutomaticOrder(String X_Token, Boolean automatic);

    @ApiOperation("查询可选订单列表接口")
    public ResultDto getFbOptionalOrder(String X_Token, Integer pageNum, Integer pageSize);

    @ApiOperation("查询历史记录接口")
    public ResultDto getFbHistorical(String X_Token, Integer pageNum, Integer pageSize);


    @ApiOperation("查询历史记录详情接口")
    public ResultDto getFbDetails(String X_Token, String id);

    @ApiOperation("确定收款接口")

    public ResultDto fbConfirm(String X_Token, String id);

    @ApiOperation("抢订单接口")

    public ResultDto robFbOrder(String X_Token, String id);
}
