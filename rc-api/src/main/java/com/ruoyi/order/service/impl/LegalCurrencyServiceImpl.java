package com.ruoyi.order.service.impl;

import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.service.LegalCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalCurrencyServiceImpl implements LegalCurrencyService {


    @Autowired
    private LegalCurrencyMapper legalCurrencyMapper;

    @Override
    public ResultDto getFbPerInformation(String X_Token) {
        Profit profit = legalCurrencyMapper.getFbPerInformation(1);

        ResultDto resultDto = new ResultDto(1, "查询成功", profit);
        return resultDto;
    }

    @Override
    public ResultDto getFbMyOrderList(String X_Token, Integer pageNum, Integer pageSize) {
        Integer userId = 1;
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrder = legalCurrencyMapper.getFbMyOrderList(userId, pageNum, pageSize);
        ResultDto resultDto = new ResultDto(1, "查询成功", frenchCurrencyOrder);
        return resultDto;
    }

    @Override
    public ResultDto getFbAutomaticOrder(String X_Token) {
        return null;
    }

    @Override
    public ResultDto editFbAutomaticOrder(String X_Token, Boolean automatic) {


        return null;
    }

    @Override
    public ResultDto getFbOptionalOrder(String X_Token, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ResultDto getFbHistorical(String X_Token, Integer pageNum, Integer pageSize) {


        Integer userId = 1;
        pageNum = (pageNum - 1) * pageSize;
        List<FrenchCurrencyOrder> frenchCurrencyOrder = legalCurrencyMapper.getFbHistorical(userId, pageNum, pageSize);
        ResultDto resultDto = new ResultDto(1, "查询成功", frenchCurrencyOrder);
        return resultDto;
    }

    @Override
    public ResultDto getFbDetails(String X_Token, String id) {

        Integer userId = 1;
        FrenchCurrencyOrder frenchCurrencyOrder =legalCurrencyMapper.getFbDetails(userId, id);
        ResultDto resultDto = new ResultDto(1, "查询成功", frenchCurrencyOrder);
        return resultDto;
    }

    @Override
    public ResultDto fbConfirm(String X_Token, String id) {
        return null;
    }

    @Override
    public ResultDto robFbOrder(String X_Token, String id) {
        return null;
    }
}
