package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcTransactionPlatform;
import com.ruoyi.digital.service.IRcTransactionPlatformService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拉取交易所数据信息
 * @author xiaoxia
 */
@Component("transactionPlatformTask")
public class RcTransactionPlatformTask {

    @Autowired
    private IRcTransactionPlatformService rcService;

    public void ryaddRC() {
//        for (int i = 1; i < 8; i++) {
            String uri = "https://dncapi.bqrank.net/api/v2/exchange/web-exchange";
            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//            paratmers.add(new BasicNameValuePair("page", i + ""));
            paratmers.add(new BasicNameValuePair("page", "1"));
            paratmers.add(new BasicNameValuePair("pagesize", "100"));
            try {
                String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
                if(result.isEmpty()){
                    System.out.println("没有数据");
                    return;
                }
                JSONObject sql = JSONObject.fromObject(result);
                String result1 = sql.getString("data");
                JSONArray jsonArray = JSONArray.fromObject(result1);
                Object[] objs = jsonArray.toArray();
                for (Object object : objs) {
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    RcTransactionPlatform data = new RcTransactionPlatform();
                    RcTransactionPlatform newData = new RcTransactionPlatform();

                    data.setCoinId((String) jsonObject.getString("id"));
                    data.setName((String) jsonObject.getString("name"));
                    data.setLogo((String) jsonObject.getString("logo"));
                    data.setRank((String) jsonObject.getString("rank"));
                    data.setPairnum((String) jsonObject.getString("pairnum"));
                    data.setVolumn(new BigDecimal(jsonObject.getString("volumn")));
                    data.setVolumnBtc(new BigDecimal(jsonObject.getString("volumn_btc")));
                    data.setVolumnCny(new BigDecimal(jsonObject.getString("volumn_cny")));
                    data.setTradeurl((String) jsonObject.getString("tradeurl"));
                    data.setChangeVolumn(new BigDecimal(jsonObject.getString("change_volumn")).toString());
                    data.setExrank((String) jsonObject.getString("exrank"));
                    data.setAssetsUsd(new BigDecimal(jsonObject.getString("assets_usd")));
                    data.setRiskLevel((String) jsonObject.getString("risk_level"));
                    data.setCreateTime(new Date());

                    RcTransactionPlatform listData=new RcTransactionPlatform();
                    listData.setCoinId(data.getCoinId());
                    List<RcTransactionPlatform> list=rcService.selectRcTransactionPlatformList(listData);
                    if (list.size() < 1) {
                        rcService.insertRcTransactionPlatform(data);
                    } else {
                        newData.setId(list.get(0).getId());
                        newData.setName((String) jsonObject.getString("name"));
                        newData.setLogo((String) jsonObject.getString("logo"));
                        newData.setRank((String) jsonObject.getString("rank"));
                        newData.setPairnum((String) jsonObject.getString("pairnum"));
                        newData.setVolumn(new BigDecimal(jsonObject.getString("volumn")));
                        newData.setVolumnBtc(new BigDecimal(jsonObject.getString("volumn_btc")));
                        newData.setVolumnCny(new BigDecimal(jsonObject.getString("volumn_cny")));
                        newData.setTradeurl((String) jsonObject.getString("tradeurl"));
                        newData.setChangeVolumn(new BigDecimal(jsonObject.getString("change_volumn")).toString());
                        newData.setExrank((String) jsonObject.getString("exrank"));
                        newData.setAssetsUsd(new BigDecimal(jsonObject.getString("assets_usd")));
                        newData.setRiskLevel((String) jsonObject.getString("risk_level"));
                        newData.setCreateTime(new Date());
                        rcService.updateRcTransactionPlatform(newData);
                    }
                }

            }catch (Exception e) {
                System.out.println("错误信息： " + e.toString());
            }
//        }

    }

}
