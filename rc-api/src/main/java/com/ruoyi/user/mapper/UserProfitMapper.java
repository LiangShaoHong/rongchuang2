package com.ruoyi.user.mapper;

import com.ruoyi.order.domain.Profit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface UserProfitMapper {


    @Update("update rc_user_profit set earned=earned + #{profit.earned},completed=completed+1,last_time=now() where user_id=#{profit.userId} and profit_type=#{profit.profitType}")
    public Integer update(@Param("profit") Profit profit);


    @Insert("insert rc_user_profit (user_id,profit_type,earned,completed,last_time) values(#{profit.userId},#{profit.profitType},#{profit.earned},1,now())")
    public Integer insert(@Param("profit") Profit profit);
}
