package com.ruoyi.order.service.impl;

import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.service.LegalCurrencyService;
import com.ruoyi.user.domain.RcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LegalCurrencyServiceImpl implements LegalCurrencyService {


    @Autowired
    private LegalCurrencyMapper legalCurrencyMapper;

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
        return null;
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

    @Override
    public Result robFbOrder(RcUser user, String id) {


        return null;
    }
}
