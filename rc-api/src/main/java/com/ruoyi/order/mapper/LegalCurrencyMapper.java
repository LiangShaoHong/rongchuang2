package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 法币Mapper接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface LegalCurrencyMapper {


    public Profit getFbPerInformation(@Param("userId") Long userId);

    public List<FrenchCurrencyOrder> getFbMyOrderList1(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<FrenchCurrencyOrder> getFbMyOrderList2(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<FrenchCurrencyOrder> getFbHistorical(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public FrenchCurrencyOrder getFbDetails(@Param("userId") Long userId, @Param("id") String id);

    public List<FrenchCurrencyOrder> getFbOptionalOrder(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public FrenchCurrencyOrder getFbOrderById(@Param("id") String id);

    @Update("UPDATE rc_french_currency_order set payment_img = #{arg1} , confirm_the_payment_time=now(),order_state=1 WHERE user_id=#{arg0} AND order_id=#{arg2}")
    public void updateFbConfirm_a(@Param("userId") Long userId, @Param("paymentImg") String paymentImg, @Param("id") String id);

    @Update("UPDATE rc_french_currency_order set confirm_collection_time=now(),order_state=5 WHERE user_id=#{arg0} AND order_id=#{arg1}")
    public void updateFbConfirm(@Param("userId") Long userId, @Param("id") String id);

    public RcFrenchCurrencyOrder selectRcFrenchCurrencyOrderByOrderId(@Param("orderId") String orderId);

    @Insert("insert into rc_appeal (user_id,order_id,appeal_content,compl_img,state,create_time)values(#{userId},#{orderId},#{appealContent},#{complImg},#{state},#{createTime})")
    public void insertFbAppealOrder(@Param("appeal") Appeal appeal);

    @Select("select count(1) from rc_appeal where order_id=#{id} and state=1")
    public Integer selectRcAppealByUserIdAndRcFrenchCurrencyOrderId(@Param("id") String id);

    @Update("UPDATE rc_french_currency_order set confirm_collection_time=now(),order_state=6 WHERE user_id=#{arg0} AND order_id=#{arg1}")
    public void updateFbAppealOrder(@Param("userId") Long userId, @Param("id") String id);

}
