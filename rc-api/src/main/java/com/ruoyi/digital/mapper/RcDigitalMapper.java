package com.ruoyi.digital.mapper;

import com.ruoyi.digital.domain.*;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author xiaoxia
 */
public interface RcDigitalMapper {

    List<RcExchangeRateDigital> getRateList();
    List<RcTransactionDataDigital> getDataList(@Param("pageNumber") Integer pageNumber, @Param("limit") Integer limit);
    List<RcNewestMarketDigital> getMarketList(@Param("pageNumber") Integer pageNumber, @Param("limit") Integer limit);
    RcTransactionInfoDigital getInfoByCode(@Param("code") String code);
    List<RcTransactionInfoDigital> getClinchList(@Param("pageNumber") Integer pageNumber, @Param("limit") Integer limit);
    List<RcTransactionPlatformDigital> getPlatformList(@Param("pageNumber") Integer pageNumber, @Param("limit") Integer limit);
    RcTransactionInfoDigital getInfoByCodeDataAll(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeD(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeW(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeThreeM(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeYdt(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeY(@Param("code") String code);
}
