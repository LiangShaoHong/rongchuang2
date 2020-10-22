package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.Profit;

/**
 * 法币Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface CurrencyMapper {


    public Profit getBbPerInformation(Integer userId);

}
