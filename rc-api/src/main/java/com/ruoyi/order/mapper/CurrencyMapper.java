package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.CurrencyOrder;
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
public interface CurrencyMapper {


    public Profit getBbPerInformation(Integer userId);

    public List<CurrencyOrder> getBbMyOrderList(@Param("userId") Integer userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<CurrencyOrder> getBbHistorical(@Param("userId") Integer userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public CurrencyOrder getBbDetails(@Param("userId") Integer userId, @Param("id") String id);

}
