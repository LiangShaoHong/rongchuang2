package com.ruoyi.order;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.order.service.CurrencyService;
import com.ruoyi.order.service.LegalCurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
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


    @ApiOperation("查询个人信息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "profitType", value = "1：获取法币", required = true)

            })

    @RequestMapping("/getBbPerInformation")
    public ResultDto getBbPerInformation(HttpServletRequest request) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.getBbPerInformation(X_Token);
        return resultDto;
    }


    @ApiOperation("查询我的订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getBbMyOrderList")
    public ResultDto getBbMyOrderList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.getBbMyOrderList(X_Token, pageNum, pageSize);
        return resultDto;
    }

    @ApiOperation("查询自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)
            })
    @RequestMapping("/getBbAutomaticOrder")
    public ResultDto getBbAutomaticOrder(HttpServletRequest request) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.getBbAutomaticOrder(X_Token);
        return resultDto;
    }

    @ApiOperation("改变自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Boolean", name = "automatic", value = "是否开启自动抢单", required = true)
            })
    @RequestMapping("/editBbAutomaticOrder")
    public ResultDto editBbAutomaticOrder(HttpServletRequest request, Boolean automatic) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.editBbAutomaticOrder(X_Token, automatic);
        return resultDto;
    }

    @ApiOperation("查询可选订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getBbOptionalOrder")
    public ResultDto getBbOptionalOrder(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.getBbOptionalOrder(X_Token, pageNum, pageSize);
        return resultDto;
    }

    @ApiOperation("查询历史记录接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getBbHistorical")
    public ResultDto getBbHistorical(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.getBbHistorical(X_Token, pageNum, pageSize);
        return resultDto;
    }


    @ApiOperation("查询历史记录详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "主键id", required = true)

            })
    @RequestMapping("/getBbDetails")
    public ResultDto getBbDetails(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = currencyService.getBbDetails(X_Token, id);
        return resultDto;
    }

    @ApiOperation("抢订单接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @RequestMapping("/robBbOrder")
    public ResultDto robBbOrder(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }
}
