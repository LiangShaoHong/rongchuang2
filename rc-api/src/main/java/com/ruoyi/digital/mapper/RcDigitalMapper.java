package com.ruoyi.digital.mapper;

import com.ruoyi.digital.domain.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaoxia
 */
public interface RcDigitalMapper {

    List<RcExchangeRateDigital> getRateList();
    List<RcTransactionDataDigital> getDataList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, String pageName, String pageType);
    List<RcNewestMarketDigital> getMarketList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, String pageName, String pageType);
    RcTransactionInfoDigital getInfoByCode(@Param("code") String code);
    List<RcTransactionInfoDigital> getClinchList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, String pageName, String pageType);
    List<RcTransactionPlatformDigital> getPlatformList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, String pageName, String pageType);
    RcTransactionInfoDigital getInfoByCodeDataAll(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeD(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeW(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeThreeM(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeYdt(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeY(@Param("code") String code);

    @Select("SELECT exchange_rate FROM rc_exchange_rate WHERE exchange_rate_code=#{code}")
    BigDecimal getRate(String code);
}
