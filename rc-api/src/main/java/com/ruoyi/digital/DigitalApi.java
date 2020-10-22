package com.ruoyi.digital;


import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页货币信息接口
 */
@Api("首页货币信息接口")
@RestController
@RequestMapping("/rc-api/digital")
public class DigitalApi {

    @ApiOperation("获取货币信息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getRateList")
    public ResultDto getRateList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询首页最新上市列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getMarketList")
    public ResultDto getMarketList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询首页成交榜额列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getClinchList")
    public ResultDto getClinchList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询首页市值行情列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getDataList")
    public ResultDto getDataList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }


    @ApiOperation("查询首页交易所列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getPlatformList")
    public ResultDto getPlatformList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }
}
