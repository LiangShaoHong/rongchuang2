package com.ruoyi.common.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.server.annotation.RequestPath;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * websocket 处理函数
 */
@Component
public class TioWsMsgHandler implements IWsMsgHandler {

    private static Logger log = LoggerFactory.getLogger(TioWsMsgHandler.class);


    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     *
     * @param request        request
     * @param httpResponse   httpResponse
     * @param channelContext channelContext
     * @return HttpResponse
     */
    @RequestPath("/websocket")
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) {

        String platformId = request.getParam("platformId");
        String userId = request.getParam("userId");

        log.info("platformId={}", platformId);

        //绑定用户
        Tio.bindUser(channelContext, userId);
        WsOnlineContext.bindUser(userId, channelContext);

        //绑定群组
        Tio.bindGroup(channelContext, platformId);
        WsOnlineContext.bingGroup(platformId, channelContext);

        return httpResponse;
    }

    /**
     * @param httpRequest    httpRequest
     * @param httpResponse   httpResponse
     * @param channelContext channelContext
     * @author tanyaowu tanyaowu
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {

    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        if (StringUtils.isNotEmpty(channelContext.userid)) {
            String userId = channelContext.userid.substring(0, channelContext.userid.length() - 1);
            log.info("用户：" + userId + "离线了");
        }
        return null;
    }

    /**
     * 字符消息（binaryType = blob）过来后会走这个方法
     *
     * @param wsRequest      wsRequest
     * @param text           text
     * @param channelContext channelContext
     * @return obj
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) {
        JSONObject json = JSON.parseObject(text);
        //心跳检测包
        if (TioServerConfig.MSG_PING.equals(json.getString("code"))) {
            WsResponse wsResponse = WsResponse.fromText(text, TioServerConfig.CHARSET);
            Tio.send(channelContext, wsResponse);
        }
        return null;
    }

}
