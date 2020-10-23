package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 法币Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface LegalCurrencyMapper {


    public Profit getFbPerInformation(Integer userId);

    public List<FrenchCurrencyOrder> getFbMyOrderList(@Param("userId") Integer userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<FrenchCurrencyOrder> getFbHistorical(@Param("userId") Integer userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
