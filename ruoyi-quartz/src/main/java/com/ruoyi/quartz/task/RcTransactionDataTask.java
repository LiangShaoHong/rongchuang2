package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcTransactionData;
import com.ruoyi.digital.service.IRcTransactionDataService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    @Async
////    @Scheduled(fixedDelay = 5000)  //间隔5秒
//    public void first() throws InterruptedException {
//        System.out.println("开始执行拉取详情数据.........................");
//        for (int i = 0; i < 10; i++) {
//            String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
//            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//            paratmers.add(new BasicNameValuePair("start",i*100 + ""));
//            try {
//                String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
//                if(result.isEmpty()){
//                    System.out.println("没有数据");
//                }
//                Thread.sleep(5000);
//                JSONArray jsonArray = JSONArray.fromObject(result);
//                Object[] objs = jsonArray.toArray();
//                for (Object object : objs) {
//                    JSONObject jsonObject = JSONObject.fromObject(object);
//                    RcTransactionData data=new RcTransactionData();
//                    RcTransactionData newData=new RcTransactionData();
//
//                    data.setName((String)jsonObject.getString("name"));
//                    data.setSymbol((String)jsonObject.getString("symbol"));
//                    data.setRank(jsonObject.getString("rank"));
//                    data.setLogo((String)jsonObject.getString("logo"));
//                    data.setLogoPng((String)jsonObject.getString("logo_png"));
//                    data.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
//                    data.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
//                    data.setVolume24hUsd(new BigDecimal(jsonObject.getString("volume_24h_usd")));
//                    data.setMarketCapUsd(new BigDecimal(jsonObject.getString("market_cap_usd")));
//                    data.setAvailableSupply(new BigDecimal(jsonObject.getString("available_supply")));
//                    data.setTotalSupply(new BigDecimal(jsonObject.getString("total_supply")));
//                    data.setMaxSupply(new BigDecimal(jsonObject.getString("max_supply")));
//                    data.setPercentChange1h((String)jsonObject.getString("percent_change_1h"));
//                    data.setPercentChange7d((String)jsonObject.getString("percent_change_7d"));
//                    data.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
//                    data.setLastUpdated((String)jsonObject.getString("last_updated"));
//
//                    RcTransactionData listData=new RcTransactionData();
//                    listData.setSymbol(data.getSymbol());
//                    List<RcTransactionData> list=rcService.selectRcTransactionDataList(listData);
//                    if (list.size() < 1) {
//                        rcService.insertRcTransactionData(data);
//                    } else {
//                        newData.setId(list.get(0).getId());
//                        newData.setSymbol((String)jsonObject.getString("symbol"));
//                        newData.setRank(jsonObject.getString("rank"));
//                        newData.setLogo((String)jsonObject.getString("logo"));
//                        newData.setLogoPng((String)jsonObject.getString("logo_png"));
//                        newData.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
//                        newData.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
//                        newData.setVolume24hUsd(new BigDecimal(jsonObject.getString("volume_24h_usd")));
//                        newData.setMarketCapUsd(new BigDecimal(jsonObject.getString("market_cap_usd")));
//                        newData.setAvailableSupply(new BigDecimal(jsonObject.getString("available_supply")));
//                        newData.setTotalSupply(new BigDecimal(jsonObject.getString("total_supply")));
//                        newData.setMaxSupply(new BigDecimal(jsonObject.getString("max_supply")));
//                        newData.setPercentChange1h((String)jsonObject.getString("percent_change_1h"));
//                        newData.setPercentChange7d((String)jsonObject.getString("percent_change_7d"));
//                        newData.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
//                        newData.setLastUpdated((String)jsonObject.getString("last_updated"));
//                        rcService.updateRcTransactionData(newData);
//                    }
//                }
//            }catch (Exception e) {
//                System.out.println("错误信息： " + e.toString());
//            }
//        }
//    }

    @Async
    public void second() throws InterruptedException {
        System.out.println("开始执行拉取详情数据.........................");
        for (int i = 0; i < 5; i++) {
            String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
            paratmers.add(new BasicNameValuePair("start",i*100 + ""));
            try {
                String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
                System.out.println("请求地址：" + uri + "?start=" + i*100 + "");
                if(result.isEmpty()){
                    System.out.println("没有数据");
                    continue;
                }
                Thread.sleep(5000);
                JSONArray jsonArray = JSONArray.fromObject(result);
                Object[] objs = jsonArray.toArray();
                List<RcTransactionData> addList = new ArrayList<>();
                List<RcTransactionData> updataList = new ArrayList<>();
                for (Object object : objs) {
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    RcTransactionData data=new RcTransactionData();
                    RcTransactionData newData=new RcTransactionData();

                    data.setName((String)jsonObject.getString("name"));
                    data.setSymbol((String)jsonObject.getString("symbol"));
                    data.setRank(jsonObject.getString("rank"));
                    data.setLogo((String)jsonObject.getString("logo"));
                    data.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
                    data.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
                    data.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
                    data.setLastUpdated((String)jsonObject.getString("last_updated"));

                    RcTransactionData listData=new RcTransactionData();
                    listData.setSymbol(data.getSymbol());
                    List<RcTransactionData> list=rcService.selectRcTransactionDataList(listData);
                    if (list.size() < 1) {
                        addList.add(data);
                    } else {
                        newData.setId(list.get(0).getId());
                        newData.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
                        newData.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
                        newData.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
                        newData.setLastUpdated((String)jsonObject.getString("last_updated"));
                        updataList.add(newData);
                    }
                }
                addList.removeIf(Objects::isNull);
                if(addList.size() != 0){
                    rcService.insertRcTransactionDataList(addList);
                }

                updataList.removeIf(Objects::isNull);
                if(updataList.size() != 0){
                    rcService.updateRcTransactionDataList(updataList);
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
