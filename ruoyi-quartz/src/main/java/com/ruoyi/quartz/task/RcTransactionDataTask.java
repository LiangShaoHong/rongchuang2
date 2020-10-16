package com.ruoyi.quartz.task;

import com.ruoyi.quartz.domain.RcTransactionData;
import com.ruoyi.quartz.service.IRcTransactionDataService;
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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



/**
 * 拉取行情数据
 * @author xiaoxia
 */
@Component("transactionDataTask")
public class RcTransactionDataTask {

    @Autowired
    private IRcTransactionDataService rcService;


    public void ryaddRC(){
        System.out.println("开始执行拉取详情数据.........................");
        String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("page","1"));
        try {
            String result = makeAPICall(uri, paratmers);
            JSONArray jsonArray = JSONArray.fromObject(result);
            if(jsonArray.size() < 1){
                System.out.println("数据为空，请重试： " + jsonArray);
                return;
            }
            Object[] objs = jsonArray.toArray();
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                RcTransactionData data=new RcTransactionData();
                data.setName((String)jsonObject.getString("name"));
                data.setSymbol((String)jsonObject.getString("symbol"));
                data.setRank(jsonObject.getString("rank"));
                data.setLogo((String)jsonObject.getString("logo"));
                data.setLogoPng((String)jsonObject.getString("logo_png"));
                data.setPriceUsd(new BigDecimal(jsonObject.getString("price_usd")));
                data.setPriceBtc(new BigDecimal(jsonObject.getString("price_btc")));
                data.setVolume24hUsd(new BigDecimal(jsonObject.getString("volume_24h_usd")));
                data.setMarketCapUsd(new BigDecimal(jsonObject.getString("market_cap_usd")));
                data.setAvailableSupply(new BigDecimal(jsonObject.getString("available_supply")));
                data.setTotalSupply(new BigDecimal(jsonObject.getString("total_supply")));
                data.setMaxSupply(new BigDecimal(jsonObject.getString("max_supply")));
                data.setPercentChange1h((String)jsonObject.getString("percent_change_1h"));
                data.setPercentChange7d((String)jsonObject.getString("percent_change_7d"));
                data.setPercentChange24h((String)jsonObject.getString("percent_change_24h"));
                data.setLastUpdated((String)jsonObject.getString("last_updated"));
                rcService.insertRcTransactionData(data);
            }
        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        } catch (Exception e) {
            System.out.println("错误信息： " + e.toString());
        }
        System.out.println(".........................结束执行拉取详情数据");
    }

    /**
     * 拉取详情数据的接口
     * @param uri           接口地址
     * @param parameters    接口参数
     * @return              数据
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
