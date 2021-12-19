package com.simple.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 13:13
 **/
public class NettyClient {
    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 9999);
    }

    public void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new NettyClientInit());
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            // 添加监听，处理重连
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
