package com.ruoyi.order.mapper;


import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 用户Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface RcUserMapper {


    @Update("UPDATE rc_french_currency_order set confirm_collection_time=now(),order_state=5 WHERE user_id=#{arg0} AND order_id=#{arg1}")
    public void updateFbConfirm(@Param("userId") Long userId, @Param("id") String id);
}
