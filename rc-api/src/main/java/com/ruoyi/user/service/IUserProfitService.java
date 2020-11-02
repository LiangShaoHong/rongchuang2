package com.ruoyi.user.service;


import com.ruoyi.order.domain.Profit;

import java.math.BigDecimal;

/**
 * 用户收益接口
 *
 * @author xiaoxia
 */
public interface IUserProfitService {

    /**
     * 修改用户收益
     *
     * @return
     */
    public Integer update(Profit profit);


    /**
     * 新增用户收益
     *
     * @return
     */
    public Integer insert(Profit profit);
}
