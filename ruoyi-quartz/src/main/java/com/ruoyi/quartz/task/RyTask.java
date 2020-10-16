package com.ruoyi.quartz.task;

import com.ruoyi.quartz.domain.RcTransactionData;
import com.ruoyi.quartz.domain.RcTransactionPlatform;
import com.ruoyi.quartz.service.IRcTransactionDataService;
import com.ruoyi.quartz.service.IRcTransactionPlatformService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    @Autowired
    private IRcTransactionDataService rcService;

    @Autowired
    private IRcTransactionPlatformService rcTransactionPlatformService;

    public void ryaddRC() {
        System.out.println("开始执行拉取详情数据.........................");
        String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("page", "1"));
        try {
            String result = makeAPICall(uri, paratmers);
            System.out.println(result);
            JSONArray jsonArray = JSONArray.fromObject(result);
            Object[] objs = jsonArray.toArray();
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                RcTransactionData data = new RcTransactionData();
                data.setName((String) jsonObject.getString("name"));
                data.setSymbol((String) jsonObject.getString("symbol"));
                rcService.insertRcTransactionData(data);
            }
        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
        System.out.println(".........................结束执行拉取详情数据");
    }

    /**
     * 拉取详情数据的接口
     *
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

    /**
     *
     */
    public void ryImportExchangeInformation() {
        for (int i = 1; i < 8; i++) {
            String uri = "https://dncapi.bqrank.net/api/v2/exchange/web-exchange";
            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
            paratmers.add(new BasicNameValuePair("page", i + ""));
            paratmers.add(new BasicNameValuePair("pagesize", "100"));
            try {
                String result = makeAPICall(uri, paratmers);
                System.out.println(result);
                JSONObject sql = JSONObject.fromObject(result);
                String result1 = sql.getString("data");


                JSONArray jsonArray = JSONArray.fromObject(result1);
                Object[] objs = jsonArray.toArray();
                for (Object object : objs) {
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    RcTransactionPlatform data = new RcTransactionPlatform();
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
                    System.out.println(data+"------------------------------------------");
//                    System.out.println("RcTransactionPlatform-------------------------------------------" + rcTransactionPlatformService.selectRcTransactionPlatformByCoinId(data.getCoinId()));
                    if (rcTransactionPlatformService.selectRcTransactionPlatformByCoinId(data.getCoinId()) == null) {
                        rcTransactionPlatformService.insertRcTransactionPlatform(data);
                    } else {
                        rcTransactionPlatformService.updateRcTransactionPlatformByCoinId(data);

                    }
                }

            } catch (IOException e) {
                System.out.println("Error: cannont access content - " + e.toString());
            } catch (URISyntaxException e) {
                System.out.println("Error: Invalid URL " + e.toString());
            }
        }

    }
}
