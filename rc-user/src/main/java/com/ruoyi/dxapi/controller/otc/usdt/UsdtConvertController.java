package com.ruoyi.dxapi.controller.otc.usdt;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.OrderNumUtil;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.dx.domain.MoneyWallet;
import com.ruoyi.dx.domain.OtcSysConf;
import com.ruoyi.dx.domain.OtcUsdtConvert;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.dto.usdt.MoneyRecordMsg;
import com.ruoyi.dx.dto.usdt.recharge.UserRechargeMsgDTO;
import com.ruoyi.dx.service.IOtcWalletService;
import com.ruoyi.dx.vo.PageParm;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.MoneyService;
import com.ruoyi.dxservice.service.OtcSysConfService;
import com.ruoyi.dxservice.service.OtcUsdtConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * usdt兑换
 */
@RestController("dxUsdtConvertController")
public class UsdtConvertController {
    private static final Logger log = LoggerFactory.getLogger(UsdtConvertController.class);

    @Resource
    private SystemUtil systemUtil;
    @Resource
    private MoneyService moneyService;
    @Resource
    private OtcSysConfService otcSysConfService;
    @Resource
    private OtcUsdtConvertService otcUsdtConvertService;


    /**
     * USDT兑换
     * @param money:兑换金额
     * @return
     */
    @RequestMapping("dx-api/otc/usdtConvert")
    public Result getChargeDetail (HttpServletRequest request,@RequestParam("money") BigDecimal money) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        String platformId = user.getPlatformId();
        if (money.compareTo(BigDecimal.ZERO) <= 0){
            return Result.isFail().msg("金额有误");
        }
        //拿到会员的钱，进行校验，提现金额不可超过钱包金额
        MoneyWallet moneyWallet = moneyService.getMoneyWallet(platformId,user.getId());
        if ((money.compareTo(moneyWallet.getMoney())) > 0){
            return Result.isFail().msg("余额不足");
        }
        //拿到otc系统配置信息
        OtcSysConf otcSysConf = otcSysConfService.getOtcSysConf(platformId, InfoConstants.REDIS_OTC_SYS_CONF);
        if (null == otcSysConf || null == otcSysConf.getUsdtPrice()){
            log.info("otc系统配置信息缺少，平台号为："+platformId);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //转换成的usdt，不四舍五入
        BigDecimal moneyUsdt = (money.divide(otcSysConf.getUsdtPrice(),2,BigDecimal.ROUND_HALF_UP));
        OtcUsdtConvert otcUsdtConvert = new OtcUsdtConvert(SnowflakeIdWorker.genIdStr(),user.getId(),platformId, OrderNumUtil.getUsdtConvertCode(),money,moneyUsdt.abs(),otcSysConf.getUsdtPrice(),null);
        otcUsdtConvert.setCreateTime(new Date());
        int i = otcUsdtConvertService.insertOtcUsdtConvert(otcUsdtConvert,money);
        if (i > 0){
            return Result.isOk().msg("提交成功");
        }
        log.info("插入兑换记录失败，兑换记录信息为:{}"+otcUsdtConvert.toString());
        return Result.isOk().msg("提交失败");
    }

    /**
     * 进入兑换记录
     */
    @PostMapping("dx-api/otc/usdtConvertList")
    public Result usdtConvertList (PageParm vo,HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        return Result.isOk().data(otcUsdtConvertService.usdtConvertList(vo, user)).msg("查询成功");
    }
}
