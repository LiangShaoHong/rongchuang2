package com.ruoyi.dxapi.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.OrderNumUtil;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.dx.domain.*;
import com.ruoyi.dxapi.common.HttpClientUtil;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SignUtils;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxapi.controller.BaseController;
import com.ruoyi.dxservice.service.MoneyService;
import com.ruoyi.dxservice.service.SysPlatformInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

@Controller("dxPayController")
public class payController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(payController.class);

    @Resource
    private SystemUtil systemUtil;
    @Resource
    private MoneyService moneyService;
    @Resource
    private SysPlatformInfoService sysPlatformInfoService;

    @RequestMapping("/dx-api/money/payAction")
    @ResponseBody
    public Result payAction(HttpServletRequest request, @RequestParam("payPlatformTypeId")String payPlatformTypeId, @RequestParam("money")BigDecimal money) throws IOException {
        //后端域名前缀
        String url = "";
        User user = systemUtil.getPlatformIdAndUserId(request);
        //获取前端域名
        String originName = super.getOrigin(request);
        com.ruoyi.common.json.JSONObject sysConf = sysPlatformInfoService.getSysConf(user.getPlatformId(),InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
        if (sysConf.containsKey("domain")){
            url = sysConf.getStr("domain");
        }
        //拿到第三方支付信息
        PayPlatform payPlatform = moneyService.getPayPlatformByPayPlatformTypeId(payPlatformTypeId);
        if (null == payPlatform){
            throw new BusinessException(MsgConstants.DATE_NEVER_INIT);
        }
        //充值金额不能越界
        if (money.compareTo(payPlatform.getMax()) > 0 || money.compareTo(payPlatform.getMin()) < 0){
            throw new BusinessException("充值金额在"+payPlatform.getMin()+"至"+payPlatform.getMax()+"之间");
        }
        //订单号
        String outTradeNo = OrderNumUtil.getOrderCode("");
        BigDecimal amount = money.setScale(2, BigDecimal.ROUND_DOWN);
        Map<String, String> paramMap = new TreeMap<>();
        paramMap.put("appId", payPlatform.getMerchantCode());
        paramMap.put("amount", amount.toString());
        paramMap.put("channelId", payPlatform.getPayType().toString());
        paramMap.put("callbackUrl", "http://"+url+"/dx-api/base/callback");
        paramMap.put("outTradeNo", outTradeNo);
        paramMap.put("successUrl", "http://"+originName);
        String sign = SignUtils.getSign(paramMap, payPlatform.getMerchantMd5());
        log.info("入参的数据   : " + paramMap.toString());
        log.info("请求的sign   : " + sign);
        paramMap.put("sign", sign);
        //请求支付地址
        String result = HttpClientUtil.sendString(payPlatform.getMerchantUrl(), paramMap, "utf-8");
        String payUrl = "";
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            //成功返回code：200
            if (200 == jsonObject.getInteger("code")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                payUrl = jsonObject1.getString("payUrl");
                //获取到支付方式
                int payType = moneyService.getPayType(payPlatformTypeId);
                //产生一条订单
                MoneyUserRechange moneyUserRechange = new MoneyUserRechange(SnowflakeIdWorker.genIdStr(),user.getPlatformId(),outTradeNo,user.getId(),amount,amount,payType,MsgConstants.RECHARGE_TYPE_TO_WALLET,null,"线上支付",null,null,null,MsgConstants.CHECK_STATUS_CHECKING,MsgConstants.IS_NO,null);
                int i = moneyService.insertMoneyUserRechange(moneyUserRechange);
                if (i > 0){
                    JSONObject obj = new JSONObject();
                    obj.put("payUrl", payUrl);
                    return Result.isOk().data(obj);
                }
                log.info("产生订单失败   : " + moneyUserRechange.toString());
            }
            log.info("code不等于200   : " + result);
        }
        return Result.isFail("下单失败");
    }

    @RequestMapping("/dx-api/base/callback")
    @ResponseBody
    public String callback(@RequestBody String request) {
        log.info("进入异步通知1111111111111111111111" + request);
        JSONObject jsonObject = JSONObject.parseObject(request);
        if (null == jsonObject) {
            log.info("未接收到异步通知1111111111111111111111");
            return "FAIL";
        }
        //成功返回
        if ("SUCCESS".equals(jsonObject.getString("payStatus"))) {
            log.info("开始验签55555555555");
            //充值订单号
            String outTradeNo = jsonObject.getString("outTradeNo");
            Map<String, String> paramMap = new TreeMap<>();
            paramMap.put("appId", jsonObject.getString("appId"));
            paramMap.put("outTradeNo", outTradeNo);
            paramMap.put("orderNo", jsonObject.getString("orderNo"));
            paramMap.put("channelId", jsonObject.getString("channelId"));
            paramMap.put("amount", jsonObject.getString("amount"));
            paramMap.put("amountTrue", jsonObject.getString("amountTrue"));
            paramMap.put("payStatus", jsonObject.getString("payStatus"));
            //拿到订单，为了拿到平台号
            MoneyUserRechange moneyUserRechange = moneyService.selectMoneyUserRechangeByOrderNum(outTradeNo, null);
            PayPlatform payPlatform = moneyService.getPayPlatformInfo(moneyUserRechange.getPlatformId(), null, jsonObject.getInteger("channelId"));
            String sign = SignUtils.getSign(paramMap,payPlatform.getMerchantMd5());
            //验签
            if (sign.equals(jsonObject.getString("sign"))) {
                log.info("验签成功66666666666");
                boolean isDo = moneyService.newLandReturnAction(jsonObject);
                if (isDo) {
                    log.info("加钱处理完成");
                    return "SUCCESS";
                }
                return "FAIL";
            }
            log.info("验签失败！！！");
            return "FAIL";
        }

        log.info("code不是SUCCESS");
        return "FAIL";
    }

}
