package com.ruoyi.common.push;

import com.ruoyi.common.net.StartTioRunner;
import com.ruoyi.common.net.TioServerConfig;
import com.ruoyi.common.net.WsOnlineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsResponse;

@Service
public class PushService {

    private static final Logger log = LoggerFactory.getLogger(PushService.class);

    @Autowired
    private StartTioRunner startTioRunner;


    /**
     * 推送给单个用户
     *
     * @param userId
     * @param message
     */
    public void sendToUser(String userId, String message) {

        ChannelContext channelContext = WsOnlineContext.getChannelContextByUser(userId);
        if (channelContext != null && !channelContext.isClosed) {
            ServerGroupContext serverGroupContext = startTioRunner.getAppStarter().getWsServerStarter().getServerGroupContext();
            WsResponse wsResponse = WsResponse.fromText(message, TioServerConfig.CHARSET);
            Tio.sendToUser(serverGroupContext, userId, wsResponse);
        }
    }


    /**
     * 推送给某组用户
     *
     * @param groupId
     * @param message
     */
    public void sendToGroup(String groupId, String message) {

        ChannelContext channelContext = WsOnlineContext.getChannelContextByGroup(groupId);
        if (channelContext != null && !channelContext.isClosed) {
            ServerGroupContext serverGroupContext = startTioRunner.getAppStarter().getWsServerStarter().getServerGroupContext();
            WsResponse wsResponse = WsResponse.fromText(message, TioServerConfig.CHARSET);
            Tio.sendToGroup(serverGroupContext, groupId, wsResponse);
        }
    }


    /**
     * 推送给所有人
     *
     * @param message
     */
    public void sendToAll(String message) {
        ServerGroupContext serverGroupContext = startTioRunner.getAppStarter().getWsServerStarter().getServerGroupContext();
        WsResponse wsResponse = WsResponse.fromText(message, TioServerConfig.CHARSET);
        Tio.sendToAll(serverGroupContext, wsResponse);
    }

}
