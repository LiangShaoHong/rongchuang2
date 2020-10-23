package com.ruoyi.quartz.task;

import com.ruoyi.digital.domain.RcNewestMarket;
import com.ruoyi.digital.service.IRcNewestMarketService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拉取最新上市数据信息
 * @author xiaoxia
 */
@Component("rcNewestMarketTask")
public class RcNewestMarketTask {
    @Autowired
    private IRcNewestMarketService rcService;

    public void ryaddRC(){
        System.out.println("开始执行拉取详情数据.........................");
        String uri = "https://dncapi.bqrank.net/api/home/web-newcoin";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("webp", "1"));
        paratmers.add(new BasicNameValuePair("page", "1"));
        paratmers.add(new BasicNameValuePair("pagesize", "100"));
        try {
            String result = QuartzHttpUtils.makeAPICall(uri, paratmers);
            if(result.isEmpty()){
                System.out.println("没有数据");
                return;
            }
            JSONObject sql = JSONObject.fromObject(result);
            JSONArray jsonArray = JSONArray.fromObject(sql.getString("data"));
            Object[] objs = jsonArray.toArray();
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                RcNewestMarket data=new RcNewestMarket();
                RcNewestMarket newData=new RcNewestMarket();

                data.setCode((String)jsonObject.getString("code"));
                data.setName((String)jsonObject.getString("name"));
                data.setMarket((String)jsonObject.getString("market"));
                data.setPlatform((String)jsonObject.getString("platform"));
                data.setPlatformName((String)jsonObject.getString("platform_name"));
                data.setTime((String)jsonObject.getString("time"));
                data.setPrice(new BigDecimal(jsonObject.getString("price")));
                data.setPriceCny(new BigDecimal(jsonObject.getString("price_cny")));
                data.setVolumn((String)jsonObject.getString("volumn"));
                data.setChangePercent(new BigDecimal(jsonObject.getString("change_percent")));
                data.setFullNameZh((String)jsonObject.getString("full_name_zh"));
                data.setVolUsd((String)jsonObject.getString("vol_usd"));
                data.setLogo((String)jsonObject.getString("logo"));
                data.setFullName((String)jsonObject.getString("full_name"));
                data.setCircualing((String)jsonObject.getString("circualing"));
                data.setIsMineable((String)jsonObject.getString("is_mineable"));
                data.setChangerateUtc((String)jsonObject.getString("changerate_utc"));
                data.setChangerateUtc8((String)jsonObject.getString("changerate_utc8"));
                data.setLastUpdate(new Date());

                RcNewestMarket listData=new RcNewestMarket();
                listData.setName(data.getName());
                List<RcNewestMarket> list=rcService.selectRcNewestMarketList(listData);
                if (list.size() < 1) {
                    rcService.insertRcNewestMarket(data);
                } else {
                    newData.setId(list.get(0).getId());
                    data.setMarket((String)jsonObject.getString("market"));
                    data.setPlatform((String)jsonObject.getString("platform"));
                    data.setPlatformName((String)jsonObject.getString("platform_name"));
                    data.setTime((String)jsonObject.getString("time"));
                    data.setPrice(new BigDecimal(jsonObject.getString("price")));
                    data.setPriceCny(new BigDecimal(jsonObject.getString("price_cny")));
                    data.setVolumn((String)jsonObject.getString("volumn"));
                    data.setChangePercent(new BigDecimal(jsonObject.getString("change_percent")));
                    data.setFullNameZh((String)jsonObject.getString("full_name_zh"));
                    data.setVolUsd((String)jsonObject.getString("vol_usd"));
                    data.setLogo((String)jsonObject.getString("logo"));
                    data.setFullName((String)jsonObject.getString("full_name"));
                    data.setCircualing((String)jsonObject.getString("circualing"));
                    data.setIsMineable((String)jsonObject.getString("is_mineable"));
                    data.setChangerateUtc((String)jsonObject.getString("changerate_utc"));
                    data.setChangerateUtc8((String)jsonObject.getString("changerate_utc8"));
                    data.setLastUpdate(new Date());
                    rcService.updateRcNewestMarket(newData);
                }
            }
        }catch (Exception e) {
            System.out.println("错误信息： " + e.toString());
        }
        System.out.println(".........................结束执行拉取详情数据");
    }
}
