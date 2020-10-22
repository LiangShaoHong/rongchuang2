package com.ruoyi.dxapi.controller.otc.usdt;

import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.core.domain.ResultUtils;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.dx.domain.OtcSysConf;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.vo.PageParm;
import com.ruoyi.dx.vo.usdt_recharge.UsdtRechargeVO;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.OtcSysConfService;
import com.ruoyi.dxservice.service.UsdtRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * usdt充值
 */
@RestController("dxUsdtRechargeController")
@RequestMapping("/dx-api/otc")
public class UsdtRechargeController {

    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private UsdtRechargeService usdtRechargeService;

    @Autowired
    private OtcSysConfService otcSysConfService;

    /**
     *
     * 充值余额
     *
     * @param vo      请求参数类
     * @param request
     * @return
     */
    @PostMapping("/usdtRecharge")
    public Result usdtRecharge(UsdtRechargeVO vo, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        if (StringUtils.isAnyEmpty(vo.getOrderImg(), vo.getOrderNum())) {
            return Result.isFail("请完善订单参数");
        }
        if (vo.getMoney().compareTo(BigDecimal.ZERO) < 1) {
            return Result.isFail("充值金额必须是正数");
        }
        OtcSysConf otcSysConf = otcSysConfService.getOtcSysConf(user.getPlatformId(), InfoConstants.REDIS_OTC_SYS_CONF);
        if(StringUtils.isEmpty(otcSysConf.getUsdtAddress())){
            return Result.isFail(MsgConstants.DATE_NEVER_INIT);
        }
        Integer result = usdtRechargeService.usdtRecharge(vo, user,otcSysConf.getUsdtAddress());
        if (1 == result) {
            return Result.isOk().msg("提交成功");
        }
        return Result.isFail("提交失败");
    }

    /**
     * 查询充值记录列表
     *
     * @param vo      请求参数类
     * @param request
     * @return
     */
    @PostMapping("/usdtRechargeList")
    public Result usdtRechargeList(PageParm vo, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        return Result.isOk().data(usdtRechargeService.usdtRechargeList(vo, user)).msg("查询成功");
    }

    /**
     *
     * 查询充值记录详情
     * @param id      请求的id
     * @return
     */
    @PostMapping("/usdtRechargeDetails")
    public Result usdtRechargeDetails(String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        return Result.isOk().data(usdtRechargeService.usdtRechargeDetails(id)).msg("查询成功");
    }

}
