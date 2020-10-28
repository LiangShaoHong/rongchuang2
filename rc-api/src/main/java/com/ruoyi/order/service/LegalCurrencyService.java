package com.ruoyi.order.service;

import com.ruoyi.common.Result;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.user.domain.RcUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 法币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("法币业务接口")
public interface LegalCurrencyService {

    @ApiOperation("查询个人信息接口")
    public Result getFbPerInformation(RcUser user);


    @ApiOperation("查询我的订单列表接口")
    public Result getFbMyOrderList(RcUser user, Integer pageNum, Integer pageSize);

    @ApiOperation("查询自动抢单状态接口")
    public Result getFbAutomaticOrder(RcUser user);

    @ApiOperation("改变自动抢单状态接口")
    public Result editFbAutomaticOrder(RcUser user, Boolean automatic);

    @ApiOperation("查询可选订单列表接口")
    public Result getFbOptionalOrder(RcUser user, Integer pageNum, Integer pageSize);

    @ApiOperation("查询历史记录接口")
    public Result getFbHistorical(RcUser user, Integer pageNum, Integer pageSize);


    @ApiOperation("查询历史记录详情接口")
    public Result getFbDetails(RcUser user, String id);

    @ApiOperation("确定付款接口")
    public Result fbConfirm_a(RcUser user, String id, String paymentImg);

    @ApiOperation("确定收款接口")
    public Result fbConfirm(RcUser user, String id);

    @ApiOperation("抢订单接口")
    public Result robFbOrder(RcUser user, String id);
}
