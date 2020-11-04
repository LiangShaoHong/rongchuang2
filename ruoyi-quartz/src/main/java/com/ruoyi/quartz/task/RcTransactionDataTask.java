package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcTransactionData;
import com.ruoyi.digital.domain.RcTransactionInfo;
import com.ruoyi.digital.service.IRcTransactionDataService;
import com.ruoyi.digital.service.IRcTransactionInfoService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 开启多线程拉取行情数据
 * @author xiaoxia
 */
@EnableAsync
@Component("transactionDataTask")
public class RcTransactionDataTask {

    @Autowired
    private IRcTransactionDataService rcService;

    @Autowired
    private IRcTransactionInfoService infoService;

    @Async
    public void second() throws InterruptedException {
        System.out.println("开始执行拉取详情数据.........................");
        for (int i = 1; i < 6; i++) {
            String uri = "https://dncapi.bqrank.net/api/coin/web-coinrank";
            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
            paratmers.add(new BasicNameValuePair("webp","1"));
            paratmers.add(new BasicNameValuePair("pagesize","100"));
            paratmers.add(new BasicNameValuePair("page",i + ""));
            paratmers.add(new BasicNameValuePair("type","-1"));
            try {
                String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
                System.out.println("请求地址：" + uri + "?start=" + i + "");
                if(result.isEmpty()){
                    System.out.println("没有数据");
                    continue;
                }
                JSONObject sql = JSONObject.fromObject(result);
                JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
                Object[] objs = jsonArray.toArray();
                List<RcTransactionData> addList = new ArrayList<>();
                List<RcTransactionInfo> infoList = new ArrayList<>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (Object object : objs) {
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    RcTransactionData data=new RcTransactionData();
                    RcTransactionInfo infoData = new RcTransactionInfo();

                    data.setName((String)jsonObject.getString("name"));
                    data.setCode((String)jsonObject.getString("code"));
                    data.setFullname((String)jsonObject.getString("fullname"));
                    data.setRank(jsonObject.getString("rank"));
                    data.setLogo((String)jsonObject.getString("logo"));
                    data.setCurrentPrice(new BigDecimal(jsonObject.getString("current_price")));
                    data.setCurrentPriceUsd(new BigDecimal(jsonObject.getString("current_price_usd")));
                    data.setMarketValue(new BigDecimal(jsonObject.getString("market_value")));
                    data.setMarketValueUsd(new BigDecimal(jsonObject.getString("market_value_usd")));
                    data.setChangePercent((String)jsonObject.getString("change_percent"));
                    data.setLastUpdated(new Date());

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
                    addList.add(data);
                }
                addList.removeIf(Objects::isNull);
                if(addList.size() != 0){
                    rcService.insertRcTransactionDataList(addList);
                }
                infoList.removeIf(Objects::isNull);
                if(infoList.size() != 0){
                    infoService.insertRcTransactionInfoList(infoList);
                }
            }catch (Exception e) {
                System.out.println("错误信息： " + e.toString());
            }
        }
    }
    public void ryaddRC() throws InterruptedException {
        second();
        System.out.println(".........................结束执行拉取详情数据");
    }
}
