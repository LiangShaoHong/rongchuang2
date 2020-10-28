package com.ruoyi.common.net;

import com.ruoyi.common.utils.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线用户上下文
 */
public class WsOnlineContext {

    private static Map<String, ChannelContext> userMap = new ConcurrentHashMap<>();
    private static Map<String, ChannelContext> groupMap = new ConcurrentHashMap<>();

    /**
     * 绑定用户
     *
     * @param userId
     * @param channelContext
     */
    public static void bindUser(String userId, ChannelContext channelContext) {
        userMap.put(userId, channelContext);
        Tio.bindUser(channelContext, userId);
    }

    /**
     * 解绑用户（释放资源）
     *
     * @param userId
     * @param channelContext
     */
    public static void unbindUser(String userId, ChannelContext channelContext) {
        if (StringUtils.isNotEmpty(userId)) {
            userMap.remove(userId);
            Tio.unbindUser(channelContext.groupContext, userId);
        }
    }

    /**
     * 绑定群组
     *
     * @param groupId
     * @param channelContext
     */
    public static void bingGroup(String groupId, ChannelContext channelContext) {
        groupMap.put(groupId, channelContext);
        Tio.bindGroup(channelContext, groupId);
    }

    /**
     * 解绑群组（释放资源）
     *
     * @param groupId
     * @param channelContext
     */
    public static void unbingGroup(String groupId, ChannelContext channelContext) {
        if (StringUtils.isNotEmpty(groupId)) {
            groupMap.remove(groupId);
            Tio.unbindGroup(groupId, channelContext);
        }
    }

    public static ChannelContext getChannelContextByUser(String userId) {
        ChannelContext channelContext = userMap.get(userId);
        if (channelContext != null && !channelContext.isClosed) {
            return Tio.getChannelContextById(channelContext.groupContext, channelContext.getId());
        } else {
            return null;
        }
    }

    public static ChannelContext getChannelContextByGroup(String groupId) {
        ChannelContext channelContext = groupMap.get(groupId);
        if (channelContext != null && !channelContext.isClosed) {
            return Tio.getChannelContextById(channelContext.groupContext, channelContext.getId());
        } else {
            return null;
        }
    }

}
