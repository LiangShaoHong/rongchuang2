package com.ruoyi.order.service.impl;

import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.mapper.CurrencyMapper;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.service.CurrencyService;
import com.ruoyi.order.service.LegalCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public ResultDto getBbPerInformation(String X_Token) {
        Profit profit = currencyMapper.getBbPerInformation(1);
        ResultDto resultDto = new ResultDto(1, "", profit);
        return resultDto;
    }

    @Override
    public ResultDto getBbMyOrderList(String X_Token, PageDomain pageDomain) {
        return null;
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
    public ResultDto getBbOptionalOrder(String X_Token, PageDomain pageDomain) {
        return null;
    }

    @Override
    public ResultDto getBbHistorical(String X_Token, PageDomain pageDomain) {
        return null;
    }

    @Override
    public ResultDto getBbDetails(String X_Token, String id) {
        return null;
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
