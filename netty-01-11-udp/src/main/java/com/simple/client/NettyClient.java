package com.simple.client;

import com.simple.init.MyChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 17:42
 **/
public class NettyClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            // 这选择 NIO的udp通道
            b.group(group).channel(NioDatagramChannel.class)
                    .handler(new MyChannelInitializer());
            Channel ch = b.bind(7398).sync().channel();
            //向目标端口发送信息
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("这里可以直接发送给服务端！！", Charset.forName("GBK")),
                    new InetSocketAddress("127.0.0.1", 7397))).sync();
            ch.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
