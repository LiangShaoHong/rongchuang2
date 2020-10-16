package com.quartz.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.net.URLConnection;


public class  Http {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return  sb.toString();
    }
    public static JSONObject postRequestFromUrl(String url, String body) throws IOException, JSONException {
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(body);
        out.flush();
        InputStream instream = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }



    public static com.alibaba.fastjson.JSONObject getRequestFromUrl(String url) throws IOException, JSONException {
        System.out.println("sadf"+url);
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        InputStream instream = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            com.alibaba.fastjson.JSONObject json = JSON.parseObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        // 请求示例 url 默认请求参数已经URL编码处理
        String url = "https://api.onebound.cn/taobao/api_call.php?key=<您自己的apiKey>&secret=<您自己的apiSecret>&api_name=item_get&num_iid=520813250866&is_promotion=1";
        //JSONObject json = getRequestFromUrl(url);
        //System.out.println(json.toString());
    }
}
