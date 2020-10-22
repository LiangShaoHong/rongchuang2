package com.ruoyi.order;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("查询个人信息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "profitType", value = "1：获取法币", required = true)

            })
    @PostMapping("/getBbPerInformation")
    public ResultDto getFbPerInformation(HttpServletRequest request, String profitType) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }


    @ApiOperation("查询我的订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getBbMyOrderList")
    public ResultDto getFbMyOrderList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "switchType", value = "开关类型 1：法币抢单开关", required = true)
            })
    @PostMapping("/getBbAutomaticOrder")
    public ResultDto getFbAutomaticOrder(HttpServletRequest request, String switchType) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("改变自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Boolean", name = "automatic", value = "是否开启自动抢单", required = true),
                    @ApiImplicitParam(dataType = "String", name = "switchType", value = "开关类型 1：法币抢单开关", required = true)
            })
    @PostMapping("/editBbAutomaticOrder")
    public ResultDto editFbAutomaticOrder(HttpServletRequest request, Boolean automatic, String switchType) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询可选订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getBbOptionalOrder")
    public ResultDto getFbOptionalOrder(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询历史记录接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getBbHistorical")
    public ResultDto getFbHistorical(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }


    @ApiOperation("查询历史记录详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "主键id", required = true)

            })
    @PostMapping("/getBbDetails")
    public ResultDto getFbDetails(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("抢订单接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @PostMapping("/robBbOrder")
    public ResultDto robFbOrder(HttpServletRequest request, String id) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }
}
