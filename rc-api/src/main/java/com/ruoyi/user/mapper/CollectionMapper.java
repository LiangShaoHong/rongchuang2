package com.ruoyi.user.mapper;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Result;
import com.ruoyi.user.domain.RcAccount;
import com.ruoyi.user.domain.RcUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 收款账户接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface CollectionMapper {


    @Select("select id,user_id userId,collection_information collectionInformation,qrcode,region,dict_type dictType,icon from rc_account where user_id=#{userId}")
    public List<RcAccount> getPersonalCollectionAccountList(@Param("userId") Long userId);

    @Insert("insert into rc_account (user_id,collection_information,qrcode,region,dict_type,icon,create_time)values(#{userId},#{json},#{qrcode},#{region},#{dictType},#{icon},now())")
    public Integer addPersonalCollection(@Param("userId") Long userId, @Param("json") String json, @Param("qrcode") String qrcode, @Param("region") String region, @Param("dictType") String dictType, @Param("icon") String icon);

    @Select("select id,user_id userId,collection_information collectionInformation,qrcode,region,dict_type dictType,icon,create_time from rc_account where user_id=#{userId} and id=#{id}")
    public RcAccount getPersonalCollectionDetails(@Param("userId") Long userId, @Param("id") Long id);

    @Update("update rc_account set collection_information=#{json},qrcode=#{qrcode},region=#{region},dict_type=#{dictType},icon=#{icon},update_time=now() where user_id=#{userId} and id=#{id}")
    public Integer updatePersonalCollection(@Param("userId") Long userId, @Param("id") Long id, @Param("json") String json, @Param("qrcode") String qrcode, @Param("region") String region, @Param("dictType") String dictType, @Param("icon") String icon);

    @Delete("delete from rc_account where user_id=#{userId} and id=#{id}")
    public Integer deletePersonalCollection(@Param("userId") Long userId, @Param("id") Long id);

}
