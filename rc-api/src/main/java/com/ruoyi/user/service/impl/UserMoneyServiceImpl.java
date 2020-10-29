package com.ruoyi.user.service.impl;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.user.domain.RcMoneyRecord;
import com.ruoyi.user.mapper.RcUserMapper;
import com.ruoyi.user.service.IRcMoneyRecordService;
import com.ruoyi.user.service.IUserMoneyService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxia
 */
@Service
public class UserMoneyServiceImpl implements IUserMoneyService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RcUserMapper rcUserMapper;

    @Autowired
    private IRcMoneyRecordService rcMoneyRecordService;

    private static final Logger log = LoggerFactory.getLogger(UserMoneyServiceImpl.class);

    @Override
    public boolean moneyDoCenter(String userId, String account, String fromUserId, BigDecimal money, BigDecimal cashHandFee, String recordType, String mark) {
        String lockKey = "lockKey:moneyDoCenter:"+userId;
        RLock fairLock = redissonClient.getFairLock(lockKey);
        try {
            // 尝试加锁，最多等待5秒，上锁以后10秒自动解锁
            if (fairLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                // 获取用户钱包余额 根据用户id 用户名
                JSONObject oldUserMoney = rcUserMapper.getUserMoney(userId,account);
                if(oldUserMoney.isEmpty()){
                    throw new BusinessException("未查到该用户");
                }
                // 1、更新用户钱包余额 根据用户id 用户名 金额
                int i = rcUserMapper.editUserMoneyWallet(oldUserMoney.getStr("id"), account, money.add(cashHandFee));
                if (i == 0) {
                    throw new BusinessException("用户余额不足");
                }

                // 2、添加资金变动记录
                // 变动前余额
                BigDecimal beforeMoney = new BigDecimal(oldUserMoney.getStr("money"));
                // 变动后金额
                BigDecimal afterMoney = beforeMoney.add(money).add(cashHandFee);
                // 加入资金变动记录
                RcMoneyRecord rcMoneyRecord = new RcMoneyRecord( userId, account, fromUserId, beforeMoney, afterMoney, money, cashHandFee, recordType, mark);
                int res = rcMoneyRecordService.insertRcMoneyRecord(rcMoneyRecord);
                if (res == 0) {
                    throw new BusinessException("添加资金变动记录操作异常");
                }
            }
        } catch (InterruptedException e) {
            log.error("加锁异常！e.getMessage():{}, e:{}", e.getMessage(), e);
        } finally {
            if (fairLock.isHeldByCurrentThread()) {
                fairLock.unlock();
            }
        }
        return true;
    }

}
