package com.ruoyi.order.service.impl;

import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.order.domain.CurrencyOrder;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.mapper.CurrencyMapper;
import com.ruoyi.order.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public ResultDto getBbPerInformation(String X_Token) {
        Profit profit = currencyMapper.getBbPerInformation(1);
        ResultDto resultDto = new ResultDto(1, "查询成功", profit);
        return resultDto;
    }

    @Override
    public ResultDto getBbMyOrderList(String X_Token, Integer pageNum, Integer pageSize) {
        Integer userId = 1;
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrder = currencyMapper.getBbMyOrderList(userId, pageNum, pageSize);
        ResultDto resultDto = new ResultDto(1, "查询成功", currencyOrder);
        return resultDto;
    }

    @Override
    public ResultDto getBbAutomaticOrder(String X_Token) {
        return null;
    }

    @Override
    public ResultDto editBbAutomaticOrder(String X_Token, Boolean automatic) {
        return null;
    }

    @Override
    public ResultDto getBbOptionalOrder(String X_Token, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ResultDto getBbHistorical(String X_Token, Integer pageNum, Integer pageSize) {
        Integer userId = 1;
        pageNum = (pageNum - 1) * pageSize;
        List<CurrencyOrder> currencyOrder = currencyMapper.getBbHistorical(userId, pageNum, pageSize);
        ResultDto resultDto = new ResultDto(1, "查询成功", currencyOrder);
        return resultDto;
    }

    @Override
    public ResultDto getBbDetails(String X_Token, String id) {
        Integer userId = 1;
        CurrencyOrder CurrencyOrder = currencyMapper.getBbDetails(userId, id);
        ResultDto resultDto = new ResultDto(1, "查询成功", CurrencyOrder);
        return resultDto;
    }

    @Override
    public ResultDto bbConfirm(String X_Token, String id) {
        return null;
    }

    @Override
    public ResultDto robBbOrder(String X_Token, String id) {
        return null;
    }
}
