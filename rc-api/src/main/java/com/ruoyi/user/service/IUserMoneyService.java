package com.ruoyi.user.service;

import java.math.BigDecimal;

/**
 * 平台加减币接口
 * @author xiaoxia
 */
public interface IUserMoneyService {

    /**
     * 加减币接口
     * @param userId        交易会员id
     * @param userName      交易会员名称
     * @param fromUserId    交易对象id(订单ID)
     * @param money         金额变化值
     * @param cashHandFee   手续费(平台佣金)
     * @param recordType    资金变化类型 0转账 1提现 2充值 3后台人员操作
     * @param mark          备注说明
     * @return
     */
    boolean moneyDoCenter(String userId, String userName, String fromUserId, BigDecimal money, BigDecimal cashHandFee, String recordType, String mark);

}
