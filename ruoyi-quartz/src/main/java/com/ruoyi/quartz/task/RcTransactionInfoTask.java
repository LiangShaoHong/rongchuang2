package com.ruoyi.quartz.task;

import com.ruoyi.digital.domain.RcTransactionInfo;
import com.ruoyi.digital.service.IRcTransactionInfoService;
import com.ruoyi.quartz.util.QuartzHttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;


/**
 * 拉取行情数据
 * @author xiaoxia
 */
@Component("rcTransactionInfoTask")
public class RcTransactionInfoTask {

    @Autowired
    private IRcTransactionInfoService rcService;

    @Async
    public void ryaddRC(){
        System.out.println("开始执行拉取详情数据.........................");
        String uri = "https://dncapi.bqrank.net/api/coin/web-coininfo";
        JSONObject params = new JSONObject();
        params.put("code", "bitcoin");
        try {
            JSONObject resultPost = QuartzHttpUtils.makeAPICallPost(uri, params);
            if(resultPost == null){
                System.out.println("没有数据");
                return;
            }
            JSONArray jsonArray = JSONArray.fromObject(resultPost);
            Object[] objs = jsonArray.toArray();
            for (Object object : objs) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                RcTransactionInfo data=new RcTransactionInfo();
                RcTransactionInfo newData=new RcTransactionInfo();

                data.setName((String)jsonObject.getString("name"));
                data.setLogo((String)jsonObject.getString("logo"));
                data.setAmountDay(new BigDecimal(jsonObject.getString("amount_day")));

                RcTransactionInfo listData=new RcTransactionInfo();
                listData.setCode(data.getCode());
                List<RcTransactionInfo> list=rcService.selectRcTransactionInfoList(listData);
                if (list.size() < 1) {
                    rcService.insertRcTransactionInfo(data);
                } else {
                    newData.setId(list.get(0).getId());
                    rcService.updateRcTransactionInfo(newData);
                }
            }
        }catch (Exception e) {
            System.out.println("错误信息： " + e.toString());
        }
        System.out.println(".........................结束执行拉取详情数据");
    }
}
