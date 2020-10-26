package com.ruoyi.order.service;

import com.ruoyi.common.Result;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.user.domain.RcUser;
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
    public Result getBbPerInformation(RcUser user);


    @ApiOperation("查询我的订单列表接口")
    public Result getBbMyOrderList(RcUser user, Integer pageNum, Integer pageSize);

    @ApiOperation("查询自动抢单状态接口")
    public Result getBbAutomaticOrder(RcUser user);

    @ApiOperation("改变自动抢单状态接口")
    public Result editBbAutomaticOrder(RcUser user, Boolean automatic);

    @ApiOperation("查询可选订单列表接口")
    public Result getBbOptionalOrder(RcUser user, Integer pageNum, Integer pageSize);

    @ApiOperation("查询历史记录接口")
    public Result getBbHistorical(RcUser user, Integer pageNum, Integer pageSize);


    @ApiOperation("查询历史记录详情接口")
    public Result getBbDetails(RcUser user, String id);

    @ApiOperation("确定收款接口")
    public Result bbConfirm(RcUser user, String id);

    @ApiOperation("抢订单接口")
    public Result robBbOrder(RcUser user, String id);
}
