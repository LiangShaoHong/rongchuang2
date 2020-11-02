package com.ruoyi.quartz.util;

import com.aeuok.task.ann.Task;
import com.aeuok.task.core.TaskContainerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.digital.domain.RcTransactionInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * @author xiaoxia
 */
public class QuartzHttpUtils {


    /**
     * @param uri        接口地址
     * @param parameters 接口参数
     * @return 数据
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String responseContent = "";
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        CloseableHttpResponse response = client.execute(request);
        try {
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return responseContent;
    }

    public static JSONObject makeAPICallPost(String url,JSONObject json){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
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

//    @Async
//    public void second() throws InterruptedException {
//        System.out.println("开始执行拉取详情数据.........................");
//        for (int i = 0; i < 5; i++) {
//            String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
//            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//            paratmers.add(new BasicNameValuePair("start",i*100 + ""));
//            try {
//                String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
//                System.out.println("请求地址：" + uri + "?start=" + i*100 + "");
//                if(result.isEmpty()){
//                    System.out.println("没有数据");
//                    continue;
//                }
//                Thread.sleep(5000);
//                JSONArray jsonArray = JSONArray.fromObject(result);
//                Object[] objs = jsonArray.toArray();
//                List<RcTransactionData> addList = new ArrayList<>();
//                List<RcTransactionData> updataList = new ArrayList<>();
//                List<RcTransactionInfo> infoList = new ArrayList<>();
//                for (Object object : objs) {
//                    JSONObject jsonObject = JSONObject.fromObject(object);
//                    RcTransactionData data=new RcTransactionData();
//                    RcTransactionData newData=new RcTransactionData();
//                    RcTransactionInfo infoData = new RcTransactionInfo();
//
//                    data.setName((String)jsonObject.getString("name"));
//                    data.setSymbol((String)jsonObject.getString("symbol"));
//                    data.setRank(jsonObject.getString("rank"));
//                    data.setLogo((String)jsonObject.getString("logo"));
//                    data.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
//                    data.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
//                    data.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
//                    data.setLastUpdated((String)jsonObject.getString("last_updated"));
//
//                    RcTransactionData listData=new RcTransactionData();
//                    listData.setSymbol(data.getSymbol());
//                    List<RcTransactionData> list=rcService.selectRcTransactionDataList(listData);
//
//                    JSONObject paramsInfo = new JSONObject();
//                    paramsInfo.put("code", jsonObject.getString("id"));
//                    JSONObject resultPost = QuartzHttpUtils.makeAPICallPost("https://dncapi.bqrank.net/api/coin/web-coininfo", paramsInfo);
//                    JSONObject resultInfo = JSONObject.fromObject(resultPost.getString("data"));
//                    if(!result.isEmpty()){
//                        infoData.setCode((String)resultInfo.getString("code"));
//                        infoData.setName((String)resultInfo.getString("name"));
//                        infoData.setFullname((String)resultInfo.getString("name_zh"));
//                        infoData.setLogo((String)resultInfo.getString("logo"));
//                        infoData.setCoindesc((String)resultInfo.getString("coindesc"));
//                        infoData.setMarketcap(new BigDecimal(resultInfo.getString("marketcap")));
//                        infoData.setMarketcapTotalUsd(new BigDecimal(resultInfo.getString("marketcap_total_usd")));
//                        infoData.setPrice(new BigDecimal(resultInfo.getString("price")));
//                        infoData.setPriceCny(new BigDecimal(resultInfo.getString("price_cny")));
//                        infoData.setChangePercent(new BigDecimal(resultInfo.getString("change_percent")));
//                        infoData.setSupply(new BigDecimal(resultInfo.getString("supply")));
//                        infoData.setTotalSupply(new BigDecimal(resultInfo.getString("totalSupply")));
//                        infoData.setCirculationRate(new BigDecimal(resultInfo.getString("circulationRate")));
//                        infoData.setAmountDay(new BigDecimal(resultInfo.getString("amount_day")));
//                        infoData.setHigh(new BigDecimal(resultInfo.getString("high")));
//                        infoData.setLow(new BigDecimal(resultInfo.getString("low")));
//                        infoData.setVol(new BigDecimal(resultInfo.getString("vol")));
//                        infoData.setTurnOver(new BigDecimal(resultInfo.getString("turn_over")));
//                        infoData.setOnlineTime(new Date());
//                        infoData.setUpdateTime(new Date());
//                        infoData.setLastUpdatatime(new Date());
//                        infoList.add(infoData);
//                    }
//                    if (list.size() < 1) {
//                        addList.add(data);
//                    } else {
//                        newData.setId(list.get(0).getId());
//                        newData.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
//                        newData.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
//                        newData.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
//                        newData.setLastUpdated((String)jsonObject.getString("last_updated"));
//                        updataList.add(newData);
//                    }
//                }
//                addList.removeIf(Objects::isNull);
//                if(addList.size() != 0){
//                    rcService.insertRcTransactionDataList(addList);
//                }
//
//                infoList.removeIf(Objects::isNull);
//                if(infoList.size() != 0){
//                    infoService.insertRcTransactionInfoList(infoList);
//                }
//
////                updataList.removeIf(Objects::isNull);
////                if(updataList.size() != 0){
////                    rcService.updateRcTransactionDataList(updataList);
////                }
//            }catch (Exception e) {
//                System.out.println("错误信息： " + e.toString());
//            }
//        }
//    }

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException {
//        String uri = "https://dncapi.bqrank.net/api/coin/web-coininfo";
//        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//        paratmers.add(new BasicNameValuePair("code", "filecoinnew"));
//        String result = makeAPICall(uri, paratmers);
//        System.out.println(result);

//        String uri = "https://dncapi.bqrank.net/api/coin/web-charts";
//        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//        paratmers.add(new BasicNameValuePair("webp", "1"));
//        paratmers.add(new BasicNameValuePair("code", "bitcoin"));
//        paratmers.add(new BasicNameValuePair("type", "all"));
//        String result = makeAPICall(uri, paratmers);
//        JSONObject sql = JSONObject.fromObject(result);
//        System.out.println(sql.get("value"));

//        String uri = "https://dncapi.bqrank.net/api/coin/web-coinrank";
//        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//        paratmers.add(new BasicNameValuePair("webp","1"));
//        paratmers.add(new BasicNameValuePair("pagesize","100"));
//        paratmers.add(new BasicNameValuePair("page","1"));
//        paratmers.add(new BasicNameValuePair("type","-1"));
//        String result = makeAPICall(uri, paratmers);
//        System.out.println(result);

//        JSONObject params = new JSONObject();
//        params.put("code", "bitcoin");
//        JSONObject resultPost = makeAPICallPost("https://dncapi.bqrank.net/api/coin/web-coininfo", params);
//        JSONObject result = JSONObject.fromObject(resultPost.getString("data"));
////        System.out.println(result);
//        ObjectMapper mapper = new ObjectMapper();
//        RcTransactionInfo info = mapper.readValue(result.toString(), RcTransactionInfo.class);
//        System.out.println(info);

//        System.out.println(!result.isEmpty());
//        System.out.println(result);

//        String uri = "https://dncapi.bqrank.net/api/home/web-newcoin";
//        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//        String result = makeAPICall(uri, paratmers);
//        JSONObject sql = JSONObject.fromObject(result);
//        JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
//        System.out.println(jsonArray);

//        for (int i = 0; i < 5; i++) {
//            String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
//            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//            paratmers.add(new BasicNameValuePair("start", i * 100 + ""));
//            try {
//                String result = makeAPICall(uri, paratmers);
//                if(result.isEmpty()){
//                    System.out.println(i + "没有数据");
//                    continue;
//                }
//            }catch (Exception e) {
//                System.out.println("错误信息： " + e.toString());
//            }
//            System.out.println(i);
//        }

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str = format.format(new Date(Long.valueOf("1603682043000")));
//        Date dd = format.parse(str);
//        System.out.println(str);
//        System.out.println(dd);

//        Date dd = format.parse(format.format(new Date(Long.valueOf("1603682043000"))));
//        System.out.println(dd);

    }
}
