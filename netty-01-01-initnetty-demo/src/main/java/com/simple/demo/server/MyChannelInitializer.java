package com.simple.demo.server;

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
    protected void initChannel(SocketChannel channel) throws Exception {
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("客户端地址:" + channel.remoteAddress().getHostString());
        System.out.println("客户端端口:" + channel.remoteAddress().getPort());
        System.out.println("链接报告完毕");
    }
}
