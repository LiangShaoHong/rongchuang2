package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSON;
import com.ruoyi.quartz.domain.RcNewestMarket;
import com.ruoyi.quartz.service.IRcNewestMarketService;
import com.ruoyi.quartz.service.IRcTransactionDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;
import java.io.IOException;
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
import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("RcNewestMarketTask")
public class RcNewestMarketTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    @Autowired
    private IRcNewestMarketService rcService;

    public void ryaddRC(){
        System.out.println("开始执行拉取详情数据.........................");
        String uri = "https://dncapi.bqrank.net/api/v3/coin/newcoinrank_list";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("page","1"));
        paratmers.add(new BasicNameValuePair("per_page","100"));
        paratmers.add(new BasicNameValuePair("sort","vol"));
        paratmers.add(new BasicNameValuePair("webp","1"));

        try {
            String result = makeAPICall(uri, paratmers);
            System.out.println(result);
            com.alibaba.fastjson.JSONObject jsonObject1 = JSON.parseObject(result);
            com.alibaba.fastjson.JSONArray jsonArray = jsonObject1.getJSONObject("data").getJSONArray("list");


            Object[] objs = jsonArray.toArray();
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);


                RcNewestMarket data=new RcNewestMarket();
                data.setCoinType((String)jsonObject.getString("symbol"));
                data.setWorldExponent((String)jsonObject.getString("price"));
                data.setDz24h((String)jsonObject.getString("changerate"));
                data.setRate("USD");
                data.setMarketTime((String)jsonObject.getString("onlinetime"));
                rcService.insertRcNewestMarket(data);
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
