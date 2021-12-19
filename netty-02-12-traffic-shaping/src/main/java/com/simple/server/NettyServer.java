package com.simple.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 13:10
 **/
public class NettyServer {
    public static void main(String[] args) {
        new NettyServer().bing(9999);
    }

    private void bing(int port) {
        /**
         * 配置服务端NIO线程组
         */
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new NettyServerInit());
            ChannelFuture f = b.bind(port).sync();
            System.out.println("服务端启动成功，等待连接到来...");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }
}