package com.ruoyi.user.service.impl;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.user.domain.RcMoneyRecord;
import com.ruoyi.user.mapper.RcUserMapper;
import com.ruoyi.user.mapper.UserProfitMapper;
import com.ruoyi.user.service.IRcMoneyRecordService;
import com.ruoyi.user.service.IUserMoneyService;
import com.ruoyi.user.service.IUserProfitService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxia
 */
@Service
public class UserProfitServiceImpl implements IUserProfitService {


    private static final Logger log = LoggerFactory.getLogger(UserProfitServiceImpl.class);


    @Autowired
    private RedissonClient redissonClient;


    @Autowired
    private UserProfitMapper userProfitMapper;


    @Override
    public Integer update(Profit profit) {

        String lockKey = "lockKey:profitUpdate:" + profit.getUserId();
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待5秒，上锁以后10秒自动解锁
            if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                return userProfitMapper.update(profit);
            }
        } catch (InterruptedException e) {
            log.error("加锁异常！e.getMessage():{}, e:{}", e.getMessage(), e);
        } finally {
            if (fairLock.isHeldByCurrentThread()) {
                fairLock.unlock();
            }
        }
        return null;
    }

    @Override
    public Integer insert(Profit profit) {
        String lockKey = "lockKey:profitInsert:" + profit.getUserId();
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待5秒，上锁以后10秒自动解锁
            if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                return userProfitMapper.insert(profit);
            }
        } catch (InterruptedException e) {
            log.error("加锁异常！e.getMessage():{}, e:{}", e.getMessage(), e);
        } finally {
            if (fairLock.isHeldByCurrentThread()) {
                fairLock.unlock();
            }
        }
        return null;
    }

}