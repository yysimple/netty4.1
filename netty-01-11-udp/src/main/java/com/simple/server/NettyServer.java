package com.simple.server;

import com.simple.init.MyChannelInitializer;
import com.simple.init.MyServerChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 21:09
 **/
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    // 广播
                    .option(ChannelOption.SO_BROADCAST, true)
                    // 设置UDP读缓冲区为2M
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)
                    // 设置UDP写缓冲区为1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    .handler(new MyServerChannelInitializer());
            ChannelFuture f = b.bind(7397).sync();
            System.out.println("服务端启动");
            f.channel().closeFuture().sync();
        } finally {
            //优雅的关闭释放内存
            group.shutdownGracefully();
        }

    }
}
