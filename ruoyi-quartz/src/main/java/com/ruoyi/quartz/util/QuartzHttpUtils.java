package com.ruoyi.quartz.util;

import com.aeuok.task.ann.Task;
import com.aeuok.task.core.TaskContainerFactory;
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
import java.util.ArrayList;
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

    public static void main(String[] args){
//        String uri = "https://dncapi.bqrank.net/api/coin/web-coininfo";
//        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//        paratmers.add(new BasicNameValuePair("code", "filecoinnew"));
//        String result = makeAPICall(uri, paratmers);
//        System.out.println(result);

//        JSONObject params = new JSONObject();
//        params.put("code", "bitcoin");
//        JSONObject resultPost = makeAPICallPost("https://dncapi.bqrank.net/api/coin/web-coininfo", params);
//        JSONArray jsonArray = JSONArray.fromObject(resultPost);
//        Object[] objs = jsonArray.toArray();
//        for (Object object : objs) {
//            JSONObject jsonObject = JSONObject.fromObject(object);
//            System.out.println(jsonObject.getString("code"));
//        }
//        System.out.println(resultPost);

//        String uri = "https://dncapi.bqrank.net/api/home/web-newcoin";
//        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
//        String result = makeAPICall(uri, paratmers);
//        JSONObject sql = JSONObject.fromObject(result);
//        JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
//        System.out.println(jsonArray);

        for (int i = 0; i < 5; i++) {
            String uri = "https://fxhapi.feixiaohao.com/public/v1/ticker";
            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
            paratmers.add(new BasicNameValuePair("start", i * 100 + ""));
            try {
                String result = makeAPICall(uri, paratmers);
                if(result.isEmpty()){
                    System.out.println(i + "没有数据");
                    continue;
                }
            }catch (Exception e) {
                System.out.println("错误信息： " + e.toString());
            }
            System.out.println(i);
        }

    }
}
