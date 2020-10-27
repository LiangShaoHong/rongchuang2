package com.ruoyi.order.service.impl;

import com.ruoyi.common.Result;
import com.ruoyi.common.utils.redis.RedisLock;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.order.domain.CurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.mapper.CurrencyMapper;
import com.ruoyi.order.service.CurrencyService;
import com.ruoyi.user.domain.RcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result getBbPerInformation(RcUser rcUser) {
        Profit profit = currencyMapper.getBbPerInformation(rcUser.getId());
        return new Result().code(1).msg("查询成功").data(profit);
    }

    @Override
    public Result getBbMyOrderList(RcUser rcUser, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrderList = currencyMapper.getBbMyOrderList(rcUser.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(currencyOrderList);
    }

    @Override
    public Result getBbAutomaticOrder(RcUser rcUser) {
        return null;
    }

    @Override
    public Result editBbAutomaticOrder(RcUser rcUser, Boolean automatic) {
        return null;
    }

    @Override
    public Result getBbOptionalOrder(RcUser rcUser, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Result getBbHistorical(RcUser rcUser, Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrderList = currencyMapper.getBbHistorical(rcUser.getId(), pageNum, pageSize);
        return new Result().code(1).msg("查询成功").data(currencyOrderList);
    }

    @Override
    public Result getBbDetails(RcUser rcUser, String id) {
        CurrencyOrder currencyOrder = currencyMapper.getBbDetails(rcUser.getId(), id);
        return new Result().code(1).msg("查询成功").data(currencyOrder);
    }

    @Override
    public Result bbConfirm(RcUser rcUser, String id) {
        return null;
    }

    @Override
    public Result robBbOrder(RcUser rcUser, String id) {

        RedisLock lock = new RedisLock(redisTemplate, "order:orderId:" + id, 5000, 10000);
        try {
            if (lock.lock()) {

//                //1：已分配  2：未分配
//                if ("2".equals(orderState)) {
//                    return new Result().data(1);
//                }
                return new Result().data("加锁");
            } else {
                return new Result().data("重新加锁");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            lock.unlock();
        }

        return new Result().data(1);
    }
}
