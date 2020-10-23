package com.ruoyi.order;


import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.order.service.LegalCurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 法币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("法币业务接口")
@RestController
@RequestMapping("/rc-api/legalCurrency")
public class LegalCurrencyApi {


    @Autowired
    private LegalCurrencyService legalCurrencyService;

    @ApiOperation("查询个人信息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)

            })
    @RequestMapping("/getFbPerInformation")
    public ResultDto getFbPerInformation(HttpServletRequest request) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.getFbPerInformation(X_Token);
        return resultDto;
    }


    @ApiOperation("查询我的订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getFbMyOrderList")
    public ResultDto getFbMyOrderList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.getFbMyOrderList(X_Token, pageNum, pageSize);
        return resultDto;
    }

    @ApiOperation("查询自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)
            })
    @RequestMapping("/getFbAutomaticOrder")
    public ResultDto getFbAutomaticOrder(HttpServletRequest request) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.getFbAutomaticOrder(X_Token);
        return resultDto;
    }

    @ApiOperation("改变自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Boolean", name = "automatic", value = "是否开启自动抢单", required = true)
            })
    @RequestMapping("/editFbAutomaticOrder")
    public ResultDto editFbAutomaticOrder(HttpServletRequest request, Boolean automatic) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.editFbAutomaticOrder(X_Token, automatic);
        return resultDto;
    }

    @ApiOperation("查询可选订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getFbOptionalOrder")
    public ResultDto getFbOptionalOrder(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.getFbOptionalOrder(X_Token, pageNum, pageSize);
        return resultDto;
    }

    @ApiOperation("查询历史记录接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getFbHistorical")
    public ResultDto getFbHistorical(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.getFbHistorical(X_Token, pageNum, pageSize);
        return resultDto;
    }


    @ApiOperation("查询历史记录详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "主键id", required = true)

            })
    @RequestMapping("/getFbDetails")
    public ResultDto getFbDetails(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.getFbDetails(X_Token, id);
        return resultDto;
    }

    @ApiOperation("确定收款接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @RequestMapping("/fbConfirm")
    public ResultDto fbConfirm(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.fbConfirm(X_Token, id);
        return resultDto;
    }

    @ApiOperation("抢订单接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @RequestMapping("/robFbOrder")
    public ResultDto robFbOrder(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        ResultDto resultDto = legalCurrencyService.robFbOrder(X_Token, id);
        return resultDto;
    }
}
