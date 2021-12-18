package com.simple.client;

import com.simple.util.MsgUtil;
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
 * @create: 2021-12-17 13:11
 **/
public class NettyClient {
    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 9999);
    }

    private void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new NettyClientInit());
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            System.out.println("客户端发起连接...");

            //测试消息，分别发放demo01、demo02、demo03
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo01(f.channel().id().toString(), "你好，消息体MsgDemo01。"));
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo02(f.channel().id().toString(), "你好，消息体MsgDemo02。"));
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo03(f.channel().id().toString(), "你好，消息体MsgDemo03。"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

