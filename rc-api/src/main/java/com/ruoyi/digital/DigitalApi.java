package com.ruoyi.digital;


import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.digital.domain.RcExchangeRate;
import com.ruoyi.digital.service.IRcDigitalService;
import com.ruoyi.digital.service.IRcExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页货币信息接口
 * @author xiaoxia
 */
@Api("首页货币信息接口")
@RestController
@RequestMapping("/rc-api/digital")
public class DigitalApi {

    @Autowired
    private IRcDigitalService rcService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private IRcExchangeRateService rcExchangeRateService;

    @ApiOperation("获取货币信息接口")
    @ApiImplicitParams(
            {

            })
    @PostMapping("/getRateList")
    public ResultDto getRateList(HttpServletRequest request) {
//        RcExchangeRate rcExchangeRate = new RcExchangeRate();
//        List<RcExchangeRate> list = rcExchangeRateService.selectRcExchangeRateList(rcExchangeRate);
//        if (redisService.exists("uid666", "rate:")) {
//            System.out.println("数据已存在");
//            List<RcExchangeRate> ll = (List<RcExchangeRate>) redisService.get("uid666","rate:");
//            System.out.println(ll);
//        }else {
//            redisService.set("uid666",list,"rate:");
//        }
        ResultDto resultDto = rcService.getRateList();
        return resultDto;
    }

    @ApiOperation("查询首页最新上市列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getMarketList")
    public ResultDto getMarketList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        ResultDto resultDto = rcService.getMarketList(pageNumber,limit);
        return resultDto;
    }

    @ApiOperation("查询首页成交榜额列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getClinchList")
    public ResultDto getClinchList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        ResultDto resultDto = rcService.getClinchList(pageNumber,limit);
        return resultDto;
    }

    @ApiOperation("查询首页市值行情列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getDataList")
    public ResultDto getDataList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        ResultDto resultDto = rcService.getDataList(pageNumber,limit);
        return resultDto;
    }

    @ApiOperation("查询首页交易所列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getPlatformList")
    public ResultDto getPlatformList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        ResultDto resultDto = rcService.getPlatformList(pageNumber,limit);
        return resultDto;
    }

    @ApiOperation("查询币种详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "code", value = "code", required = true)
            })
    @PostMapping("/getInfoByCode")
    public ResultDto getInfoByCode(HttpServletRequest request, String code) {
        ResultDto resultDto = rcService.getInfoByCode(code);
        return resultDto;
    }

}
