package com.ruoyi.dxapi.controller.otc.common;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.dto.PageDTO;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.dx.domain.MoneyWallet;
import com.ruoyi.dx.domain.OtcSysConf;
import com.ruoyi.dx.domain.OtcWallet;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.dto.usdt.order.OtcUserSaleOrderListDTO;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.MoneyService;
import com.ruoyi.dxservice.service.OtcOrderService;
import com.ruoyi.dxservice.service.OtcSysConfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * otc公共接口
 */
@RestController("dxOtcCommonController")
public class OtcCommonController {

    private static final Logger log = LoggerFactory.getLogger(OtcCommonController.class);

    @Resource
    private SystemUtil systemUtil;
    @Resource
    private OtcSysConfService otcSysConfService;
    @Resource
    private MoneyService moneyService;
    @Resource
    private OtcOrderService otcOrderService;


    /**
     * 获取系统配置信息接口
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/systemConfig")
    public Result systemConfig(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        OtcSysConf otcSysConf = otcSysConfService.getOtcSysConf(user.getPlatformId(), InfoConstants.REDIS_OTC_SYS_CONF);
        if (null == otcSysConf || null == otcSysConf.getBuyProfit() || null == otcSysConf.getSaleProfit()|| null == otcSysConf.getUsdtPrice()){
            log.error("otc系统配置信息缺少，查找的平台号为：{}", user.getPlatformId());
            return Result.isFail(MsgConstants.DATE_NEVER_INIT);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("usdtAddress",otcSysConf.getUsdtAddress());
        jsonObject.put("cashFee",otcSysConf.getCashFee());
        jsonObject.put("agreement",otcSysConf.getAgreement());
        jsonObject.put("usdtPrice",otcSysConf.getUsdtPrice());
        BigDecimal buyProfitPercent = BigDecimal.valueOf(otcSysConf.getBuyProfit()).divide(BigDecimal.valueOf(100));
        BigDecimal saleProfitPercent = BigDecimal.valueOf(otcSysConf.getSaleProfit()).divide(BigDecimal.valueOf(100));
        //买入价格（比市场价低）
        BigDecimal buyPrice = (otcSysConf.getUsdtPrice().subtract(otcSysConf.getUsdtPrice().multiply(buyProfitPercent))).setScale(4,BigDecimal.ROUND_DOWN);
        //卖出价格（比市场价高）
        BigDecimal salePrice = (otcSysConf.getUsdtPrice().add(otcSysConf.getUsdtPrice().multiply(saleProfitPercent))).setScale(4,BigDecimal.ROUND_DOWN);
        jsonObject.put("buyPrice",buyPrice);
        jsonObject.put("salePrice",salePrice);
        return Result.isOk().data(jsonObject);
    }

    /**
     * 获取当前余额接口
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/userWallet")
    public Result userWallet(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        OtcSysConf otcSysConf = otcSysConfService.getOtcSysConf(user.getPlatformId(), InfoConstants.REDIS_OTC_SYS_CONF);
        if (null == otcSysConf || null == otcSysConf.getUsdtPrice()){
            log.error("otc系统配置信息缺少，查找的平台号为：{}", user.getPlatformId());
            return Result.isFail(MsgConstants.DATE_NEVER_INIT);
        }
        //余额钱包
        MoneyWallet moneyWallet = moneyService.getMoneyWallet(user.getPlatformId(), user.getId());
        //usdt钱包
        OtcWallet otcWallet = otcOrderService.selectOtcWalletByUserIdAndMoneyCode(user.getId(), MsgConstants.MoneyCode.USDT.getKey());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("usdt",otcWallet.getMoney());
        jsonObject.put("money",moneyWallet.getMoney());
        jsonObject.put("price",otcSysConf.getUsdtPrice());
        return Result.isOk().data(jsonObject);
    }

    /**
     * 判断当前用户是否是客商
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/judge")
    public Result userWalletTravellingTrader(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        boolean result = otcSysConfService.userIsWalletTravellingTrader(user.getId());
        return Result.isOk().data(result ? 1 : 0).msg("查询成功");
    }

}
