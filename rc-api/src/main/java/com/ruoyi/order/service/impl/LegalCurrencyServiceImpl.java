package com.ruoyi.order.service.impl;

import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.common.utils.redis.RedisLock;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.ruoyi.order.service.LegalCurrencyService;
import com.ruoyi.user.domain.RcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LegalCurrencyServiceImpl implements LegalCurrencyService {


    @Autowired
    private LegalCurrencyMapper legalCurrencyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RcFrenchCurrencyOrderMapper rcFrenchCurrencyOrderMapper;

    @Autowired
    private RcFrenchCurrencyOrderReleaseMapper rcFrenchCurrencyOrderReleaseMapper;

    @Override
    public Result getFbPerInformation(RcUser user) {
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
        return null;
    }

    @Override
    public Result editFbAutomaticOrder(RcUser user, Boolean automatic) {


        return null;
    }

    @Override
    public Result getFbOptionalOrder(RcUser user, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrderList = legalCurrencyMapper.getFbOptionalOrder(user.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrderList);
    }

    @Override
    public Result getFbHistorical(RcUser user, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrderList = legalCurrencyMapper.getFbHistorical(user.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrderList);
    }

    @Override
    public Result getFbDetails(RcUser user, String id) {
        FrenchCurrencyOrder frenchCurrencyOrder = legalCurrencyMapper.getFbDetails(user.getId(), id);
        return new Result().code(1).msg("查询成功").data(frenchCurrencyOrder);
    }

    @Override
    public Result fbConfirm(RcUser user, String id) {
        return null;
    }


    @Autowired
    private RedisService redisService;


    @Override
    public Result robFbOrder(RcUser user, String id) {
        String lockKey = Constants.DB_ORDER + id;
        RedisLock lock = new RedisLock(redisTemplate, lockKey, 5000, 10000);
        try {
            if (lock.lock()) {
                FrenchCurrencyOrder frenchCurrencyOrder = legalCurrencyMapper.getFbOrderById(id);
                if (1 == frenchCurrencyOrder.getOrderState()) {
                    return Result.isFail("订单已被领取");
                }
                if (2 == frenchCurrencyOrder.getOrderState()) {
                    RcFrenchCurrencyOrder rcFrenchCurrencyOrder = new RcFrenchCurrencyOrder();
                    String orderId = UUID.randomUUID().toString().replace("-", "");
                    rcFrenchCurrencyOrder.setOrderId(orderId);
                    rcFrenchCurrencyOrder.setRcFrenchCurrencyOrderReleaseId(Long.valueOf(frenchCurrencyOrder.getId()));
                    rcFrenchCurrencyOrder.setUserId(user.getId());
                    rcFrenchCurrencyOrder.setCreateTime(new Date());
                    rcFrenchCurrencyOrder.setOrderState("3");
                    rcFrenchCurrencyOrderMapper.insertRcFrenchCurrencyOrder(rcFrenchCurrencyOrder);

                    RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease = new RcFrenchCurrencyOrderRelease();
                    rcFrenchCurrencyOrderRelease.setId(Long.valueOf(id));
                    rcFrenchCurrencyOrderRelease.setOrderState("1");
                    rcFrenchCurrencyOrderReleaseMapper.updateRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease);
                    return Result.isOk().msg("提交成功");

                }
            } else {
                return Result.isFail("订单已被领取");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            lock.unlock();
        }
        return null;
    }
}
