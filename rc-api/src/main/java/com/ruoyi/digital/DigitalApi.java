package com.ruoyi.digital;


import com.ruoyi.common.Result;
import com.ruoyi.common.utils.redis.RedisService;
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
    public Result getRateList(HttpServletRequest request) {
        return rcService.getRateList();
    }

    @ApiOperation("查询首页最新上市列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getMarketList")
    public Result getMarketList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        return rcService.getMarketList(pageNumber,limit);
    }

    @ApiOperation("查询首页成交榜额列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getClinchList")
    public Result getClinchList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        return rcService.getClinchList(pageNumber,limit);
    }

    @ApiOperation("查询首页市值行情列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getDataList")
    public Result getDataList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        return rcService.getDataList(pageNumber,limit);
    }

    @ApiOperation("查询首页交易所列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getPlatformList")
    public Result getPlatformList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        return rcService.getPlatformList(pageNumber,limit);
    }

    @ApiOperation("查询币种详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "code", value = "code", required = true)
            })
    @PostMapping("/getInfoByCode")
    public Result getInfoByCode(HttpServletRequest request, String code) {
        return rcService.getInfoByCode(code);
    }

}
