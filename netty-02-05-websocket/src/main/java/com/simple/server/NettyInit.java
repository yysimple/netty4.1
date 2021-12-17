package com.simple.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 10:36
 **/
public class NettyInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        // http的内置服务端编解码handler
        channel.pipeline().addLast("http-codec", new HttpServerCodec());
        // 为握手提供聚合的HttpRequest
        channel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        // ChunkedWriteHandler：它支持异步写大型数据流，而又不会导致大量的内存消耗
        channel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new NettyServerChannelHandler());
    }

}
