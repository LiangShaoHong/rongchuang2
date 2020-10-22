package com.ruoyi.dxapi.controller.otc.usdt;

import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.dx.domain.OtcSysConf;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.vo.PageParm;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.OtcSysConfService;
import com.ruoyi.dxservice.service.UsdtWithdrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * usdt提现
 */
@RestController("dxUsdtWithdrawController")
@RequestMapping("/dx-api/otc")
public class UsdtWithdrawController {

    @Resource
    private SystemUtil systemUtil;

    @Resource
    private OtcSysConfService otcSysConfService;

    @Autowired
    private UsdtWithdrawService usdtWithdrawService;

    /**
     * 客商发起 usdt 提现
     * @param request
     * @param money     提现金额
     * @param address   提现地址
     * @param type      提现类别
     * @return
     */
    @PostMapping("/usdtWithdraw")
    public Result usdtWithdraw(HttpServletRequest request,
                               @RequestParam("money")BigDecimal money,
                               @RequestParam("address")String address,
                               @RequestParam("type")Integer type ) {

        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        if(StringUtils.isAnyEmpty(address)){
            return Result.isFail("请完善收款地址");
        }
        if(money.compareTo(BigDecimal.ZERO) <= 0){
            return Result.isFail("金额有误");
        }

        OtcSysConf otcSysConf = otcSysConfService.getOtcSysConf(user.getPlatformId(), InfoConstants.REDIS_OTC_SYS_CONF);
        if(null == otcSysConf){
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        if(null == otcSysConf.getSaleProfit() || otcSysConf.getSaleProfit() < 0){
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        if(null == otcSysConf.getCashFee()){
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //获取比率
        String nowRate = String.valueOf(Double.valueOf(otcSysConf.getCashFee()) / 100);
        //计算提现所需的手续费
        BigDecimal cashFee = money.multiply(new BigDecimal(nowRate));
        //客商usdt提现数据
        Integer result = usdtWithdrawService.usdtWithdraw(user,money,address,type,cashFee);
        if(1 == result){
            return Result.isOk().msg("提交成功");
        }
        return Result.isFail("提交失败");
    }

    /**
     * 查询客商自己的提现记录
     * @param request
     * @return
     */
    @PostMapping("/usdtWithdrawList")
    public Result usdtWithdrawList(HttpServletRequest request, @RequestParam("pageNumber")Integer pageNumber,
            @RequestParam("limit")Integer limit,@RequestParam(value = "status",required = false)Integer status) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        TableDataInfo tableDataInfo = usdtWithdrawService.selectUsdtWithdrawList(user,pageNumber,limit,status);
        return Result.isOk().data(tableDataInfo).msg("查询成功");
    }
}