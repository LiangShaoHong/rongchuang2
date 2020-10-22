package com.ruoyi.dxapi.common;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.*;

import static oshi.util.ParseUtil.byteArrayToHexString;

//签名的工具类
public class SignUtils {

    public static String createSign(String characterEncoding, Map<String, Object> parameters, String Key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key"+ "=" +Key);
        System.out.println("sb：   " + sb);
        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public static String getOutPutSring(SortedMap<Object, Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"tranAttr".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
            if ("tranAttr".equals(k)){
                sb.append(k + "=" + v);
            }
        }
        return sb.toString();
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return resultString;

    }

    public static String getSign(Map map, String key) {

        String sign = createSign("UTF-8", map, key);//生成签名
        return sign;
    }

    public static Map<String,String> getParasMap(HttpServletRequest request){
        Map<String, String> params = new HashMap<String, String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        String name=null;
        String[] values=null;
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            name =  iter.next();
            values =  requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr
                        + values[i] : valueStr + values[i] + ",";
            }
            System.out.println(name+":"+valueStr);
            params.put(name, valueStr);
        }
        return params;
    }

    // 验签
    public static boolean verify( SortedMap<Object, Object> params,String key) {
        boolean flag = false;
        String mysign = getSign(params,key);
        String sign = "";
        if (params.get("sign") != null) {
            sign = (String) params.get("sign");
        }
        // 验签
        flag = mysign.equals(sign);
        return flag;
    }


}
