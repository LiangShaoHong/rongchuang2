package com.ruoyi.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.common.utils.redis.RedisLock;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.service.ConfigService;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.ruoyi.order.service.LegalCurrencyService;
import com.ruoyi.user.domain.RcUser;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LegalCurrencyServiceImpl implements LegalCurrencyService {

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(LegalCurrencyServiceImpl.class);

    @Autowired
    private LegalCurrencyMapper legalCurrencyMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RcFrenchCurrencyOrderMapper rcFrenchCurrencyOrderMapper;

    @Autowired
    private RcFrenchCurrencyOrderReleaseMapper rcFrenchCurrencyOrderReleaseMapper;

    @Autowired
    private SenderService senderService;


    @Autowired
    private ConfigService configService;

    @Autowired
    private PushService pushService;

    @Override
    public Result getFbPerInformation(RcUser user) {
        log.info("调用法币个人信息接口");
        Profit profit = legalCurrencyMapper.getFbPerInformation(user.getId());
        return new Result().code(1).msg("查询成功").data(profit);
    }

    @Override
    public Result getFbMyOrderList(RcUser user, Integer pageNum, Integer pageSize) {

        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrderList = legalCurrencyMapper.getFbMyOrderList(user.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrderList);
    }

    @Override
    public Result getFbAutomaticOrder(RcUser user) {
        log.info("调用法币查询自动抢单状态接口");

        return null;
    }

    @Override
    public Result editFbAutomaticOrder(RcUser user, Boolean automatic) {
        log.info("调用法币改变自动抢单状态接口");

        return null;
    }

    @Override
    public Result getFbOptionalOrder(RcUser user, Integer pageNum, Integer pageSize) {
        log.info("调用法币查询可选订单列表接口");
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrderList = legalCurrencyMapper.getFbOptionalOrder(user.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrderList);
    }

    @Override
    public Result getFbHistorical(RcUser user, Integer pageNum, Integer pageSize) {
        log.info("调用法币查询历史记录接口");
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrderList = legalCurrencyMapper.getFbHistorical(user.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrderList);
    }

    @Override
    public Result getFbDetails(RcUser user, String id) {
        log.info("调用法币查询历史记录详情接口");
        FrenchCurrencyOrder frenchCurrencyOrder = legalCurrencyMapper.getFbDetails(user.getId(), id);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrder);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result fbConfirm_a(RcUser user, String id, String paymentImg) {
        log.info("调用法币确认付款接口");
        RcFrenchCurrencyOrder rcFrenchCurrencyOrder = legalCurrencyMapper.selectRcFrenchCurrencyOrderByOrderId(id);
        //3：订单状态为待确认付款
        if ("3".equals(rcFrenchCurrencyOrder.getOrderState())) {
            String lockKey = "lockKey:" + Constants.DB_ORDER + id;
            RLock fairLock = redissonClient.getFairLock(lockKey);
            try {
                // 尝试加锁，最多等待l秒，上锁以后l1秒自动解锁
                if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                    legalCurrencyMapper.updateFbConfirm_a(user.getId(), paymentImg, id);
                    RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease = rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseById(rcFrenchCurrencyOrder.getRcFrenchCurrencyOrderReleaseId());
                    // websocket 通知后台 有用户登陆 首页页面输出用户名和音乐
                    JSONObject msg = new JSONObject();
                    msg.put("msg", "有新的法币订单需要确认收款");
                    pushService.sendToUser(String.valueOf(rcFrenchCurrencyOrderRelease.getSellerUserId()), msg.toString());

                    //获取法币未确认付款超时时间（毫秒）
                    String unpaidOvertime = configService.getKey("rc.legalCurrency.uncollectedOvertime");
                    JSONObject data = new JSONObject();
                    data.put("orderId", id);
                    senderService.sendQueueDelayMessage(JmsConstant.queueUncollectedOvertime, data, Integer.valueOf(unpaidOvertime));


                    return new Result().code(1).msg("提交成功").data("");
                }
            } catch (InterruptedException e) {
                log.error("加锁异常！e.getMessage():{}, e:{}", e.getMessage(), e);
            } finally {
                if (fairLock.isHeldByCurrentThread())
                    fairLock.unlock();
            }
        }
         if ("4".equals(rcFrenchCurrencyOrder.getOrderState())) {
            return Result.isFail("订单确认付款已超时");
        }
        return Result.isFail("提交失败");
    }

    @Override
    public Result fbConfirm(RcUser user, String id) {
        log.info("调用法币确认收款接口");
        RcFrenchCurrencyOrder rcFrenchCurrencyOrder = legalCurrencyMapper.selectRcFrenchCurrencyOrderByOrderId(id);
        //1：订单状态为待确认收款
        if ("1".equals(rcFrenchCurrencyOrder.getOrderState())) {
            String lockKey = "lockKey:" + Constants.DB_ORDER + id;
            RLock fairLock = redissonClient.getFairLock(lockKey);
            try {
                // 尝试加锁，最多等待l秒，上锁以后l1秒自动解锁
                if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                    legalCurrencyMapper.updateFbConfirm(user.getId(), id);
                    return new Result().code(1).msg("提交成功").data("");
                }
            } catch (InterruptedException e) {
                log.error("加锁异常！e.getMessage():{}, e:{}", e.getMessage(), e);
            } finally {
                if (fairLock.isHeldByCurrentThread())
                    fairLock.unlock();
            }
        }
        if ("2".equals(rcFrenchCurrencyOrder.getOrderState())) {
            return Result.isFail("订单确认收款已超时");
        }
        return Result.isFail("提交失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result robFbOrder(RcUser user, String id) {
        log.info("调用法币抢订单接口");
        String lockKey = "lockKey:" + Constants.DB_ORDER + id;
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待l秒，上锁以后l1秒自动解锁
            if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                FrenchCurrencyOrder frenchCurrencyOrder = legalCurrencyMapper.getFbOrderById(id);
                if (1 == frenchCurrencyOrder.getOrderState()) {
                    log.info("法币订单已被领取");
                    return Result.isFail("订单已被领取");
                }
                if (2 == frenchCurrencyOrder.getOrderState()) {
                    log.info("当法币订单处于未领取状态时");
                    RcFrenchCurrencyOrder rcFrenchCurrencyOrder = new RcFrenchCurrencyOrder();
                    String orderId = UUID.randomUUID().toString().replace("-", "");
                    rcFrenchCurrencyOrder.setOrderId(orderId);
                    rcFrenchCurrencyOrder.setRcFrenchCurrencyOrderReleaseId(Long.valueOf(frenchCurrencyOrder.getId()));
                    rcFrenchCurrencyOrder.setUserId(user.getId());
                    Date createTimeDate = new Date();
                    rcFrenchCurrencyOrder.setCreateTime(createTimeDate);
                    rcFrenchCurrencyOrder.setOrderState("3");
                    rcFrenchCurrencyOrderMapper.insertRcFrenchCurrencyOrder(rcFrenchCurrencyOrder);
                    RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease = rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseById(Long.valueOf(id));
                    rcFrenchCurrencyOrderRelease.setOrderState("1");
                    rcFrenchCurrencyOrderReleaseMapper.updateRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease);
                    // websocket 通知后台 有用户登陆 首页页面输出用户名和音乐
                    JSONObject msg = new JSONObject();
                    msg.put("msg", "有新的法币订单需要确认付款");
                    pushService.sendToUser(String.valueOf(user.getId()), msg.toString());

                    //获取法币未确认付款超时时间（毫秒）
                    String unpaidOvertime = configService.getKey("rc.legalCurrency.unpaidOvertime");
                    JSONObject data = new JSONObject();
                    data.put("orderId", orderId);
                    senderService.sendQueueDelayMessage(JmsConstant.queueUnpaidOvertime, data, Integer.valueOf(unpaidOvertime));
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
