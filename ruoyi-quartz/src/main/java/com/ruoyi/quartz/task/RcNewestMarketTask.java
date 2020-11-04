package com.ruoyi.quartz.task;

import com.ruoyi.digital.domain.RcNewestMarket;
import com.ruoyi.digital.domain.RcTransactionData;
import com.ruoyi.digital.domain.RcTransactionInfo;
import com.ruoyi.digital.service.IRcNewestMarketService;
import com.ruoyi.digital.service.IRcTransactionInfoService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 拉取最新上市数据信息
 * @author xiaoxia
 */
@Component("rcNewestMarketTask")
public class RcNewestMarketTask {

    @Autowired
    private IRcNewestMarketService rcService;

    @Autowired
    private IRcTransactionInfoService infoService;

    @Async
    public void ryaddRC(){
        System.out.println("开始执行拉取详情数据.........................");
        String uri = "https://dncapi.bqrank.net/api/home/web-newcoin";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("webp", "1"));
        paratmers.add(new BasicNameValuePair("page", "1"));
        paratmers.add(new BasicNameValuePair("pagesize", "100"));
        try {
            String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
            if(result.isEmpty()){
                System.out.println("没有数据");
                return;
            }
            JSONObject sql = JSONObject.fromObject(result);
            JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
            Object[] objs = jsonArray.toArray();
            List<RcNewestMarket> addList = new ArrayList<>();
            List<RcTransactionInfo> infoList = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                RcNewestMarket data=new RcNewestMarket();
                RcTransactionInfo infoData = new RcTransactionInfo();

                data.setCode((String)jsonObject.getString("code"));
                data.setName((String)jsonObject.getString("name"));
                data.setLogo((String)jsonObject.getString("logo"));
                data.setFullName((String)jsonObject.getString("full_name"));
                data.setTime((String)jsonObject.getString("time"));
                data.setPrice(new BigDecimal(jsonObject.getString("price")));
                data.setPriceCny(new BigDecimal(jsonObject.getString("price_cny")));
                data.setChangePercent(new BigDecimal(jsonObject.getString("change_percent")));
                data.setLastUpdate(new Date());
                addList.add(data);

                JSONObject paramsInfo = new JSONObject();
                paramsInfo.put("code", data.getCode());
                Thread.sleep(5000);
                JSONObject resultPost = QuartzHttpUtils.makeAPICallPost("https://dncapi.bqrank.net/api/coin/web-coininfo", paramsInfo);
                JSONObject resultInfo = JSONObject.fromObject(resultPost.getString("data"));
                if(!resultInfo.isEmpty()){
                    infoData.setCode((String)resultInfo.getString("code"));
                    infoData.setName((String)resultInfo.getString("name"));
                    infoData.setFullname((String)resultInfo.getString("name_zh"));
                    infoData.setSymbol((String)resultInfo.getString("symbol"));
                    infoData.setLogo((String)resultInfo.getString("logo"));
                    infoData.setCoindesc((String)resultInfo.getString("coindesc"));
                    infoData.setMarketcap(new BigDecimal(resultInfo.getString("marketcap")));
                    infoData.setMarketcapTotalUsd(new BigDecimal(resultInfo.getString("marketcap_total_usd")));
                    infoData.setPrice(new BigDecimal(resultInfo.getString("price")));
                    infoData.setPriceCny(new BigDecimal(resultInfo.getString("price_cny")));
                    infoData.setChangePercent(new BigDecimal(resultInfo.getString("change_percent")));
                    infoData.setSupply(new BigDecimal(resultInfo.getString("supply")));
                    infoData.setTotalSupply(new BigDecimal(resultInfo.getString("totalSupply")));
                    infoData.setCirculationRate(new BigDecimal(resultInfo.getString("circulationRate")));
                    infoData.setAmountDay(new BigDecimal(resultInfo.getString("amount_day")));
                    infoData.setHigh(new BigDecimal(resultInfo.getString("high")));
                    infoData.setLow(new BigDecimal(resultInfo.getString("low")));
                    infoData.setVol(new BigDecimal(resultInfo.getString("vol")));
                    infoData.setTurnOver(new BigDecimal(resultInfo.getString("turn_over")));
                    infoData.setOnlineTime((String)resultInfo.getString("online_time"));
                    infoData.setUpdateTime(format.parse(format.format(new Date(Long.valueOf((String)resultInfo.getString("updatetime") + "000")))));
                    infoData.setLastUpdatatime(new Date());
                    infoList.add(infoData);
                }

//                RcNewestMarket newData=new RcNewestMarket();

//                data.setCode((String)jsonObject.getString("code"));
//                data.setName((String)jsonObject.getString("name"));
//                data.setMarket((String)jsonObject.getString("market"));
//                data.setPlatform((String)jsonObject.getString("platform"));
//                data.setPlatformName((String)jsonObject.getString("platform_name"));
//                data.setTime((String)jsonObject.getString("time"));
//                data.setPrice(new BigDecimal(jsonObject.getString("price")));
//                data.setPriceCny(new BigDecimal(jsonObject.getString("price_cny")));
//                data.setVolumn((String)jsonObject.getString("volumn"));
//                data.setChangePercent(new BigDecimal(jsonObject.getString("change_percent")));
//                data.setFullNameZh((String)jsonObject.getString("full_name_zh"));
//                data.setVolUsd((String)jsonObject.getString("vol_usd"));
//                data.setLogo((String)jsonObject.getString("logo"));
//                data.setFullName((String)jsonObject.getString("full_name"));
//                data.setCircualing((String)jsonObject.getString("circualing"));
//                data.setIsMineable((String)jsonObject.getString("is_mineable"));
//                data.setChangerateUtc((String)jsonObject.getString("changerate_utc"));
//                data.setChangerateUtc8((String)jsonObject.getString("changerate_utc8"));
//                data.setLastUpdate(new Date());

//                RcNewestMarket listData=new RcNewestMarket();
//                listData.setName(data.getName());
//                List<RcNewestMarket> list=rcService.selectRcNewestMarketList(listData);
//                if (list.size() < 1) {
//                    rcService.insertRcNewestMarket(data);
//                } else {
//                    newData.setId(list.get(0).getId());
//                    data.setMarket((String)jsonObject.getString("market"));
//                    data.setPlatform((String)jsonObject.getString("platform"));
//                    data.setPlatformName((String)jsonObject.getString("platform_name"));
//                    data.setTime((String)jsonObject.getString("time"));
//                    data.setPrice(new BigDecimal(jsonObject.getString("price")));
//                    data.setPriceCny(new BigDecimal(jsonObject.getString("price_cny")));
//                    data.setVolumn((String)jsonObject.getString("volumn"));
//                    data.setChangePercent(new BigDecimal(jsonObject.getString("change_percent")));
//                    data.setFullNameZh((String)jsonObject.getString("full_name_zh"));
//                    data.setVolUsd((String)jsonObject.getString("vol_usd"));
//                    data.setLogo((String)jsonObject.getString("logo"));
//                    data.setFullName((String)jsonObject.getString("full_name"));
//                    data.setCircualing((String)jsonObject.getString("circualing"));
//                    data.setIsMineable((String)jsonObject.getString("is_mineable"));
//                    data.setChangerateUtc((String)jsonObject.getString("changerate_utc"));
//                    data.setChangerateUtc8((String)jsonObject.getString("changerate_utc8"));
//                    data.setLastUpdate(new Date());
//                    rcService.updateRcNewestMarket(newData);
//                }
            }
            addList.removeIf(Objects::isNull);
            if(addList.size() != 0){
                rcService.insertRcNewestMarketList(addList);
            }
            infoList.removeIf(Objects::isNull);
            if(infoList.size() != 0){
                infoService.insertRcTransactionInfoList(infoList);
            }
        }catch (Exception e) {
            System.out.println("错误信息： " + e.toString());
        }
        System.out.println(".........................结束执行拉取详情数据");
    }
}
