package com.ruoyi.quartz.task;


import com.ruoyi.digital.domain.RcExchangeRate;
import com.ruoyi.digital.service.IRcExchangeRateService;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
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
            String result = QuartzHttpUtils.makeAPICall(uri, new ArrayList<NameValuePair>());
            if(result.isEmpty()){
                System.out.println("没有数据");
                return;
            }
            JSONObject sql = JSONObject.fromObject(result);
            JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
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
        }catch (Exception e) {
            System.out.println("错误信息： " + e.toString());
        }
        System.out.println(".........................结束执行拉取汇率数据");
    }

}
