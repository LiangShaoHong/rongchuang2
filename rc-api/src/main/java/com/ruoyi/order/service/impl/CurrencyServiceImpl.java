package com.ruoyi.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.redis.RedisLock;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.service.ConfigService;
import com.ruoyi.order.domain.*;
import com.ruoyi.order.mapper.*;
import com.ruoyi.order.service.CurrencyService;
import com.ruoyi.user.domain.RcUser;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private RcCurrencyOrderMapper rcCurrencyOrderMapper;

    @Autowired
    private RcCurrencyOrderReleaseMapper rcCurrencyOrderReleaseMapper;

    @Autowired
    private SenderService senderService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private PushService pushService;


    @Override
    public Result getBbPerInformation(RcUser rcUser) {
        log.info("调用币币查询个人信息接口");
        Profit profit = currencyMapper.getBbPerInformation(rcUser.getId());
        if (profit == null) {
            profit = new Profit();
            profit.setEarned(new BigDecimal(0));
            profit.setBalance(rcUser.getMoney());
            profit.setCompleted(0);
        }
        return new Result().code(1).msg("查询成功").data(profit);
    }

    @Override
    public Result getBbMyOrderList(RcUser rcUser, Integer pageNum, Integer pageSize) {
        log.info("调用币币查询我的订单列表接口");
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrderList = currencyMapper.getBbMyOrderList(rcUser.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(currencyOrderList);
    }

    @Override
    public Result getBbAutomaticOrder(HttpServletRequest request, RcUser rcUser) {
        log.info("调用币币查询自动抢单状态接口");
        HttpSession session = request.getSession();
        Boolean automatic = (Boolean) session.getAttribute("BB Automatic order grabbing switch" + rcUser.getAccount());
        JSONObject jsonObject = new JSONObject();
        if (automatic == null) {
            jsonObject.put("automatic", false);
        } else {
            jsonObject.put("automatic", automatic);
        }
        return new Result().isOk().data(jsonObject);
    }

    @Override
    public Result editBbAutomaticOrder(HttpServletRequest request, RcUser rcUser, Boolean automatic) {
        log.info("调用币币改变自动抢单状态接口");
        HttpSession session = request.getSession();
        if (automatic) {
            session.setAttribute("BB Automatic order grabbing switch" + rcUser.getAccount(), true);
        } else {
            session.setAttribute("BB Automatic order grabbing switch" + rcUser.getAccount(), false);
        }
        return new Result().isOk().msg("提交成功");
    }

    @Override
    public Result getBbOptionalOrder(RcUser rcUser, Integer pageNum, Integer pageSize) {
        log.info("调用币币查询可选订单列表接口");
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrderList = currencyMapper.getBbOptionalOrder(rcUser.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(currencyOrderList);
    }

    @Override
    public Result getBbHistorical(RcUser rcUser, Integer pageNum, Integer pageSize) {
        log.info("调用币币查询历史记录接口");
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrderList = currencyMapper.getBbHistorical(rcUser.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(currencyOrderList);
    }

    @Override
    public Result getBbDetails(RcUser rcUser, String id) {
        log.info("调用币币查询历史记录详情接口");
        CurrencyOrder currencyOrder = currencyMapper.getBbDetails(rcUser.getId(), id);
        return new Result().code(1).msg("查询成功").data(currencyOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result robBbOrder(RcUser user, String id) {
        log.info("调用币币抢订单接口");
        String lockKey = "lockKey:" + Constants.DB_ORDER + "Bb:" + id;
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待l秒，上锁以后l1秒自动解锁
            if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                CurrencyOrder currencyOrder = currencyMapper.getBbOrderById(id);
                if (1 == currencyOrder.getOrderState()) {
                    log.info("币币订单已被领取");
                    return Result.isFail("订单已被领取");
                }
                if (2 == currencyOrder.getOrderState()) {
                    log.info("当币币订单处于未领取状态时");
                    RcCurrencyOrder rcCurrencyOrder = new RcCurrencyOrder();
                    String orderId = UUID.randomUUID().toString().replace("-", "");
                    rcCurrencyOrder.setOrderId(orderId);
                    rcCurrencyOrder.setRcCurrencyOrderReleaseId(Long.valueOf(currencyOrder.getId()));
                    rcCurrencyOrder.setUserId(user.getId());
                    Date createTimeDate = new Date();
                    rcCurrencyOrder.setCreateTime(createTimeDate);
                    rcCurrencyOrder.setOrderState("3");
                    rcCurrencyOrderMapper.insertRcCurrencyOrder(rcCurrencyOrder);
                    RcCurrencyOrderRelease rcCurrencyOrderRelease = rcCurrencyOrderReleaseMapper.selectRcCurrencyOrderReleaseById(Long.valueOf(id));
                    rcCurrencyOrderRelease.setOrderState("1");
                    rcCurrencyOrderReleaseMapper.updateRcCurrencyOrderRelease(rcCurrencyOrderRelease);

                    //获取币币未确认付款超时时间（毫秒）
                    String unpaidOvertime = configService.getKey("rc.currency.overtime");
                    JSONObject data = new JSONObject();
                    data.put("orderId", orderId);
                    data.put("user", user);
                    senderService.sendQueueDelayMessage(JmsConstant.queueBbOvertime, data, Integer.valueOf(unpaidOvertime));
                    return Result.isOk().msg("提交成功");
                }
            }
        } catch (InterruptedException e) {
            log.error("加锁异常！e.getMessage():{}, e:{}", e.getMessage(), e);
        } finally {
            if (fairLock.isHeldByCurrentThread())
                fairLock.unlock();
        }
        return null;
    }
}
