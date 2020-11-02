package com.ruoyi.digital.mapper;

import com.ruoyi.digital.domain.*;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author xiaoxia
 */
public interface RcDigitalMapper {

    List<RcExchangeRateDigital> getRateList();
    List<RcTransactionDataDigital> getDataList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,String pageName,String pageType);
    List<RcNewestMarketDigital> getMarketList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, String pageName, String pageType);
    RcTransactionInfoDigital getInfoByCode(@Param("code") String code);
    List<RcTransactionInfoDigital> getClinchList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,String pageType);
    List<RcTransactionPlatformDigital> getPlatformList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    RcTransactionInfoDigital getInfoByCodeDataAll(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeD(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeW(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeThreeM(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeYdt(@Param("code") String code);
    RcTransactionInfoDigital getInfoByCodeY(@Param("code") String code);
}
