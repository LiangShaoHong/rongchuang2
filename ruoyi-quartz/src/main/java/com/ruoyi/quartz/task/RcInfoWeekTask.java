package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcTransactionInfo;
import com.ruoyi.digital.mapper.RcTransactionInfoMapper;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 拉取汇率详情
 * @author xiaoxia
 */
@Component("infoWeekTask")
public class RcInfoWeekTask {

    @Autowired
    private RcTransactionInfoMapper rcTransactionInfoMapper;

    @Autowired
    private RedisService redis;

    @Async
    public void second() throws InterruptedException {
        System.out.println("开始执行拉取详情数据.........................");
        for (int i = 1; i < 6; i++) {
            String uri = "https://dncapi.bqrank.net/api/coin/web-coinrank";
            List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
            paratmers.add(new BasicNameValuePair("webp","1"));
            paratmers.add(new BasicNameValuePair("pagesize","100"));
            paratmers.add(new BasicNameValuePair("page",i + ""));
            paratmers.add(new BasicNameValuePair("type","-1"));
            try {
                String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
                System.out.println("请求地址：" + uri + "?start=" + i + "");
                if(result.isEmpty()){
                    System.out.println("没有数据");
                    continue;
                }
                Thread.sleep(5000);
                JSONObject sql = JSONObject.fromObject(result);
                JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
                Object[] objs = jsonArray.toArray();
                List<RcTransactionInfo> infoList = new ArrayList<>();
                for (Object object : objs) {
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    RcTransactionInfo infoData = new RcTransactionInfo();

                    List<NameValuePair> list_all = new ArrayList<>();
                    list_all.add(new BasicNameValuePair("webp", "1"));
                    list_all.add(new BasicNameValuePair("code", (String)jsonObject.getString("code")));
                    list_all.add(new BasicNameValuePair("type", "w"));
                    JSONObject resultInfo = JSONObject.fromObject(QuartzHttpUtils.makeAPICall("https://dncapi.bqrank.net/api/coin/web-charts", list_all));
                    if(!resultInfo.isEmpty()){
                        infoData.setCode((String)jsonObject.getString("code"));
                        infoData.setInfo_w((String)resultInfo.getString("value"));
                        infoList.add(infoData);
                    }
                }
                infoList.removeIf(Objects::isNull);
                if(infoList.size() != 0){
                    rcTransactionInfoMapper.updateWeek(infoList);
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
