package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcExchangeRate;
import com.ruoyi.digital.service.IRcExchangeRateService;
import com.ruoyi.framework.redis.RedisService;
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
 * 拉取汇率详情
 * @author xiaoxia
 */
@Component("exchangeTateTask")
public class RcExchangeTateTask {

    @Autowired
    private IRcExchangeRateService rcService;

    @Autowired
    private RedisService redis;

    public void ryaddRC(){

        System.out.println("开始执行拉取汇率数据.........................");
        String uri = "https://dncapi.bqrank.net/api/coin/web-rate";
        try {
            String result = makeAPICall(uri, new ArrayList<NameValuePair>());
            JSONObject sql = JSONObject.fromObject(result);
            JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
            if(jsonArray.size() < 1){
                System.out.println("数据为空，请重试： " + jsonArray);
                return;
            }
            Object[] objs = jsonArray.toArray();
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                RcExchangeRate data=new RcExchangeRate();
                RcExchangeRate newData=new RcExchangeRate();

                data.setExchangeRateCode(jsonObject.keys().next().toString());
                BigDecimal rate = new BigDecimal(jsonObject.getString(jsonObject.keys().next().toString()));
                data.setExchangeRate(rate);
                data.setUpdateTime(new Date());

                RcExchangeRate listData=new RcExchangeRate();
                listData.setExchangeRateCode(jsonObject.keys().next().toString());
                List<RcExchangeRate> list=rcService.selectRcExchangeRateList(listData);
                if (list.size() < 1) {
                    rcService.insertRcExchangeRate(data);
                } else {
                    newData.setId(list.get(0).getId());
                    newData.setExchangeRate(rate);
                    newData.setUpdateTime(new Date());
                    rcService.updateRcExchangeRate(newData);
                }

            }
        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        } catch (Exception e) {
            System.out.println("错误信息： " + e.toString());
        }
        System.out.println(".........................结束执行拉取汇率数据");
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
