package com.simple.coderutil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 10:19
 **/
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            // 如果是客户端，则添加 HttpClientCodec
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            // 如果是服务器，则添加 HttpServerCodec
            pipeline.addLast("codec", new HttpServerCodec());
        }
        // 将最大的消息大小为 512 KB的 HttpObjectAggregator 添加到 ChannelPipeline
        pipeline.addLast("aggregator",
                new HttpObjectAggregator(512 * 1024));
    }

}
