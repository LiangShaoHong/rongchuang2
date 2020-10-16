package com.quartz.controller;

import com.alibaba.fastjson.JSONObject;
import com.quartz.util.Http;
import junit.framework.TestCase;
import org.json.JSONException;
import org.junit.jupiter.api.Test;


import java.io.IOException;


public class NewestTransactionTest {


    @Test
    public void huoqu() throws IOException, JSONException {
        Http http=new Http();
        JSONObject JSL=http.getRequestFromUrl("https://dncapi.bqrank.net/api/v2/ranking/coinvol?per_page=30&page=1&sort=vol&webp=1");
        System.out.println(JSL);

    }
}