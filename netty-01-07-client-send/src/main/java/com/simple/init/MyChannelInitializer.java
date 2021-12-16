package com.simple.init;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:45
 **/
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        System.out.println("本客户端链接到服务端。channelId：" + channel.id());
    }
}
