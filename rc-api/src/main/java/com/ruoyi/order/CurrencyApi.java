package com.ruoyi.order;

import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.order.service.CurrencyService;
import com.ruoyi.user.domain.RcUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 币币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("币币业务接口")
@RestController
@RequestMapping("/rc-api/currency")
public class CurrencyApi {

    @Autowired
    private CurrencyService currencyService;

    @Resource
    private SystemUtil systemUtil;

    @ApiOperation("查询个人信息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "profitType", value = "1：获取法币", required = true)

            })

    @PostMapping("/getBbPerInformation")
    public Result getBbPerInformation(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.getBbPerInformation(user);
    }


    @ApiOperation("查询我的订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @PostMapping("/getBbMyOrderList")
    public Result getBbMyOrderList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.getBbMyOrderList(user, pageNum, pageSize);
    }

    @ApiOperation("查询自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)
            })
    @PostMapping("/getBbAutomaticOrder")
    public Result getBbAutomaticOrder(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.getBbAutomaticOrder(user);
    }

    @ApiOperation("改变自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Boolean", name = "automatic", value = "是否开启自动抢单", required = true)
            })
    @PostMapping("/editBbAutomaticOrder")
    public Result editBbAutomaticOrder(HttpServletRequest request, Boolean automatic) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.editBbAutomaticOrder(user, automatic);
    }

    @ApiOperation("查询可选订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @PostMapping("/getBbOptionalOrder")
    public Result getBbOptionalOrder(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.getBbOptionalOrder(user, pageNum, pageSize);
    }

    @ApiOperation("查询历史记录接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @PostMapping("/getBbHistorical")
    public Result getBbHistorical(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.getBbHistorical(user, pageNum, pageSize);
    }


    @ApiOperation("查询历史记录详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "主键id", required = true)

            })
    @PostMapping("/getBbDetails")
    public Result getBbDetails(HttpServletRequest request, String id) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.getBbDetails(user, id);
    }

    @ApiOperation("抢订单接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @PostMapping("/robBbOrder")
    public Result robBbOrder(HttpServletRequest request, String id) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return currencyService.robBbOrder(user, id);
    }
}
