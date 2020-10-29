package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.*;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 币币Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface CurrencyMapper {


    public Profit getBbPerInformation(Long userId);

    public List<CurrencyOrder> getBbMyOrderList(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<CurrencyOrder> getBbHistorical(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public CurrencyOrder getBbDetails(@Param("userId") Long userId, @Param("id") String id);


    public CurrencyOrder getBbOrderById(@Param("id") String id);

    public RcCurrencyOrder selectRcCurrencyOrderByOrderId(@Param("orderId") String orderId);

}
