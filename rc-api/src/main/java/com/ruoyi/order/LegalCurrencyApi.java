package com.ruoyi.order;


import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.service.LegalCurrencyService;
import com.ruoyi.user.domain.RcUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private ServerConfig serverConfig;

    @ApiOperation("查询个人信息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)

            })
    @RequestMapping("/getFbPerInformation")
    public Result getFbPerInformation(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.getFbPerInformation(user);
    }


    @ApiOperation("查询我的订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getFbMyOrderList")
    public Result getFbMyOrderList(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.getFbMyOrderList(user, pageNum, pageSize);
    }

    @ApiOperation("查询自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)
            })
    @RequestMapping("/getFbAutomaticOrder")
    public Result getFbAutomaticOrder(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.getFbAutomaticOrder(user);
    }

    @ApiOperation("改变自动抢单状态接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Boolean", name = "automatic", value = "是否开启自动抢单", required = true)
            })
    @RequestMapping("/editFbAutomaticOrder")
    public Result editFbAutomaticOrder(HttpServletRequest request, Boolean automatic) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.editFbAutomaticOrder(user, automatic);
    }

    @ApiOperation("查询可选订单列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getFbOptionalOrder")
    public Result getFbOptionalOrder(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.getFbOptionalOrder(user, pageNum, pageSize);
    }

    @ApiOperation("查询历史记录接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页大小", required = true)
            })
    @RequestMapping("/getFbHistorical")
    public Result getFbHistorical(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.getFbHistorical(user, pageNum, pageSize);
    }


    @ApiOperation("查询历史记录详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "主键id", required = true)

            })
    @RequestMapping("/getFbDetails")
    public Result getFbDetails(HttpServletRequest request, String id) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.getFbDetails(user, id);
    }

    @ApiOperation("确定付款接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true),
                    @ApiImplicitParam(dataType = "String", name = "paymentImg", value = "付款截图", required = true)

            })
    @RequestMapping("/fbConfirm_a")
    public Result fbConfirm_a(HttpServletRequest request, String id, String paymentImg) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.fbConfirm_a(user, id, paymentImg);
    }

    @ApiOperation("确定收款接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @RequestMapping("/fbConfirm")
    public Result fbConfirm(HttpServletRequest request, String id) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.fbConfirm(user, id);
    }

    @ApiOperation("抢订单接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "id", value = "订单id", required = true)

            })
    @RequestMapping("/robFbOrder")
    public Result robFbOrder(HttpServletRequest request, String id) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        return legalCurrencyService.robFbOrder(user, id);
    }
}
