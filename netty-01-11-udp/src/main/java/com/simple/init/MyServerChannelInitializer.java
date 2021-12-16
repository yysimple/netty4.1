package com.simple.init;

import com.simple.handler.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 21:22
 **/
public class MyServerChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    private final EventLoopGroup group = new NioEventLoopGroup();

    @Override
    protected void initChannel(NioDatagramChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(group, new MyServerHandler());
    }
}
