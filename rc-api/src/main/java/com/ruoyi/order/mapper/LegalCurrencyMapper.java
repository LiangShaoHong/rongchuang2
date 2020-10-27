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


    public Profit getFbPerInformation(Long userId);

    public List<FrenchCurrencyOrder> getFbMyOrderList(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<FrenchCurrencyOrder> getFbHistorical(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public FrenchCurrencyOrder getFbDetails(@Param("userId") Long userId, @Param("id") String id);

    public List<FrenchCurrencyOrder> getFbOptionalOrder(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public FrenchCurrencyOrder getFbOrderById(@Param("id") String id);
}
