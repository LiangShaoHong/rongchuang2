package com.ruoyi.order.service.impl;

import com.ruoyi.common.Result;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.order.domain.CurrencyOrder;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.mapper.CurrencyMapper;
import com.ruoyi.order.service.CurrencyService;
import com.ruoyi.user.domain.RcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    private CurrencyMapper currencyMapper;

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
        return null;
    }
}
