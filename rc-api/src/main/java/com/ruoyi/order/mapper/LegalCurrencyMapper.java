package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;

import java.util.List;

/**
 * 法币Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface LegalCurrencyMapper {


    public Profit getFbPerInformation(Integer userId);

}
