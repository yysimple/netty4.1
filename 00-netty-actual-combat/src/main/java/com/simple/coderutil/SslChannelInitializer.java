package com.simple.coderutil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 10:11
 **/
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean startTls;

    /**
     * 如果设置为 true，第一个
     * 写入的消息将不会被加密
     * （客户端应该设置为 true）
     *
     * @param context
     * @param startTls
     */
    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 对于每个 SslHandler 实例，都使用 Channel 的 ByteBufAllocator 从 SslContext 获取一个新的 SSLEngine
        SSLEngine engine = context.newEngine(ch.alloc());
        ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
