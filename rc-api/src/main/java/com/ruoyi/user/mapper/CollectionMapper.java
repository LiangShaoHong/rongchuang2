package com.ruoyi.user.mapper;


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


    @Select("select id,user_id userId,collection_information collectionInformation,qrcode,region from rc_account where user_id=#{userId}")
    public List<RcAccount> getPersonalCollectionAccountList(@Param("userId") Long userId);

    @Insert("insert into rc_account (user_id,collection_information,qrcode,region,create_time)values(#{userId},#{json},#{qrcode},#{region},now())")
    public Integer addPersonalCollection(@Param("userId") Long userId, @Param("json") String json, @Param("qrcode") String qrcode, @Param("region") String region);

    @Update("update rc_account set collection_information=#{json},qrcode=#{qrcode},region=#{region},update_time=now() where user_id=#{userId} and id=#{id}")
    public Integer updatePersonalCollection(@Param("userId") Long userId, @Param("id") Long id, @Param("json") String json, @Param("qrcode") String qrcode, @Param("region") String region);

    @Delete("delete from rc_account where user_id=#{userId} and id=#{id}")
    public Integer deletePersonalCollection(@Param("userId") Long userId, @Param("id") Long id);

}
