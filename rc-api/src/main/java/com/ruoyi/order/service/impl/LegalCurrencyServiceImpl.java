package com.ruoyi.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.service.ConfigService;
import com.ruoyi.framework.web.service.DictService;
import com.ruoyi.order.domain.*;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.ruoyi.order.service.LegalCurrencyService;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.service.IRcUserService;
import com.ruoyi.user.service.IUserMoneyService;
import com.ruoyi.user.service.IUserProfitService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
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

    @Autowired
    private IUserMoneyService iUserMoneyService;

    @Autowired
    private IRcUserService iRcUserService;

    @Autowired
    private IUserProfitService iUserProfitService;

    @Autowired
    private DictService dictService;

    @Override
    public Result getFbPerInformation(RcUser user) {
        log.info("调用法币个人信息接口");
        Profit profit = legalCurrencyMapper.getFbPerInformation(user.getId());
        if (profit == null) {
            profit = new Profit();
            profit.setEarned(new BigDecimal(0));
            profit.setBalance(user.getMoney());
            profit.setCompleted(0);
        }
        return new Result().code(1).msg("查询成功").data(profit);
    }

    @Override
    public Result getFbMyOrderList(RcUser user, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrderList = new ArrayList<FrenchCurrencyOrder>();
        List<FrenchCurrencyOrder> frenchCurrencyOrderList1 = legalCurrencyMapper.getFbMyOrderList1(user.getId(), pageNum, pageSize);
        List<FrenchCurrencyOrder> frenchCurrencyOrderList2 = legalCurrencyMapper.getFbMyOrderList2(user.getId(), pageNum, pageSize);
        frenchCurrencyOrderList.addAll(frenchCurrencyOrderList1);
        frenchCurrencyOrderList.addAll(frenchCurrencyOrderList2);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrderList);
    }

    @Override
    public Result getFbAutomaticOrder(HttpServletRequest request, RcUser user) {
        log.info("调用法币查询自动抢单状态接口");
        HttpSession session = request.getSession();
        Boolean automatic = (Boolean) session.getAttribute("FB Automatic order grabbing switch" + user.getAccount());
        JSONObject jsonObject = new JSONObject();
        if (automatic == null) {
            jsonObject.put("automatic", false);
        } else {
            jsonObject.put("automatic", automatic);
        }
        return new Result().isOk().data(jsonObject);
    }

    @Override
    public Result editFbAutomaticOrder(HttpServletRequest request, RcUser user, Boolean automatic) {
        log.info("调用法币改变自动抢单状态接口");
        HttpSession session = request.getSession();
        if (automatic) {
            session.setAttribute("FB Automatic order grabbing switch" + user.getAccount(), true);
        } else {
            session.setAttribute("FB Automatic order grabbing switch" + user.getAccount(), false);
        }
        return new Result().isOk().msg("提交成功");
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

                    //获取法币未确认收款超时时间（毫秒）
                    String unpaidOvertime = configService.getKey("rc.legalCurrency.uncollectedOvertime");
                    JSONObject data = new JSONObject();
                    data.put("orderId", id);
                    senderService.sendQueueDelayMessage(JmsConstant.queueFbUncollectedOvertime, data, Integer.valueOf(unpaidOvertime));


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
    @Transactional(rollbackFor = Exception.class)
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

                    RcUser srcUser = iRcUserService.selectRcUserById(rcFrenchCurrencyOrder.getUserId());

                    //修改用户余额
                    RcFrenchCurrencyOrderRelease rcCurrencyOrderRelease = rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseById(rcFrenchCurrencyOrder.getRcFrenchCurrencyOrderReleaseId());
                    Long money = rcCurrencyOrderRelease.getNumber() + rcCurrencyOrderRelease.getProfit();
                    iUserMoneyService.moneyDoCenter(String.valueOf(srcUser.getId()), srcUser.getAccount(), rcFrenchCurrencyOrder.getOrderId(), new BigDecimal(money), new BigDecimal(0), "4", "法币交易");
                    //修改用户收益
                    Profit profit = new Profit();
                    profit.setUserId(user.getId().intValue());
                    profit.setProfitType("1");
                    profit.setEarned(new BigDecimal(rcCurrencyOrderRelease.getProfit()));
                    Integer x = iUserProfitService.update(profit);
                    if (x <= 0) {
                        iUserProfitService.insert(profit);
                    }
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
                    senderService.sendQueueDelayMessage(JmsConstant.queueFbUnpaidOvertime, data, Integer.valueOf(unpaidOvertime));
                    Map<String, String> map = new HashMap<>();
                    map.put("orderId", orderId);
                    map.put("state", "3");
                    return Result.isOk().msg("提交成功").data(map);
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

    @Override
    public Result getFbAppealReasonType() {
        log.info("调用法币获取申诉理由类型接口");
        List<SysDictData> sysDictDataList = dictService.getType("rc_appeal_type");
        return new Result().code(1).msg("查询成功").data(sysDictDataList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result fbAppealOrder(RcUser user, String id, String appealContent, String complImg) {
        log.info("调用法币订单申诉接口");
        String lockKey = "lockKey:" + Constants.DB_ORDER + id;
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待l秒，上锁以后l1秒自动解锁
            if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                FrenchCurrencyOrder frenchCurrencyOrder = legalCurrencyMapper.getFbDetails(user.getId(), id);
                if (2 == frenchCurrencyOrder.getOrderState() || 4 == frenchCurrencyOrder.getOrderState()) {
                    Integer count = legalCurrencyMapper.selectRcAppealByUserIdAndRcFrenchCurrencyOrderId(id);
                    if (count > 0) {
                        return Result.isFail("订单已经在申诉中");
                    } else {
                        Appeal appeal = new Appeal();
                        appeal.setUserId(user.getId());
                        appeal.setOrderId(id);
                        appeal.setAppealContent(appealContent);
                        appeal.setComplImg(complImg);
                        appeal.setState(1);
                        appeal.setCreateTime(new Date());
                        legalCurrencyMapper.insertFbAppealOrder(appeal);
                        legalCurrencyMapper.updateFbAppealOrder(user.getId(), id);
                    }
                    return Result.isOk().msg("提交成功");
                } else if (6 == frenchCurrencyOrder.getOrderState()) {
                    return Result.isFail("订单已经在申诉中");
                } else {
                    return Result.isFail("订单不允许申诉");
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
