package com.simple.init;

import com.simple.handler.MyClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:45
 **/
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    @Override
    protected void initChannel(NioDatagramChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new MyClientHandler());
    }
}
