package com.simple.ws;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * 功能描述: 为 ChannelPipeline 添加加密
 *
 * @author: WuChengXing
 * @create: 2021-12-16 12:32
 **/
public class SecureChatServerInitializer extends ChatServerInitializer {
    private final SslContext context;

    /**
     * 扩展 ChatServerInitializer以添加加密
     *
     * @param group
     * @param context
     */
    public SecureChatServerInitializer(ChannelGroup group, SslContext context) {
        super(group);
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 调用父类的initChannel()方法
        super.initChannel(ch);
        // 加密操作
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(engine));
    }
}
