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

            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(), "protobuf的简单使用！！！"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(), "protobuf的简单使用！！！"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(), "protobuf的简单使用！！！"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(), "protobuf的简单使用！！！"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(), "protobuf的简单使用！！！"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
