package com.simple.handler;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 17:31
 **/
public class BatchUser {
    /**
     * 用于存放用户Channel信息，也可以建立map结构模拟不同的消息群
     */
    public static ChannelGroup USERS_YYDS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
