package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcTransactionPlatform;
import com.ruoyi.digital.service.IRcTransactionPlatformService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
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
                String result = makeAPICall(uri, paratmers);
                JSONObject sql = JSONObject.fromObject(result);
                String result1 = sql.getString("data");
                JSONArray jsonArray = JSONArray.fromObject(result1);
                if(jsonArray.size() < 1){
                    System.out.println("数据为空，请重试： " + jsonArray);
                    return;
                }
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

            } catch (IOException e) {
                System.out.println("Error: cannont access content - " + e.toString());
            } catch (URISyntaxException e) {
                System.out.println("Error: Invalid URL " + e.toString());
            }
//        }

    }

    /**
     * @param uri        接口地址
     * @param parameters 接口参数
     * @return 数据
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        CloseableHttpResponse response = client.execute(request);
        try {
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return response_content;
    }

}
