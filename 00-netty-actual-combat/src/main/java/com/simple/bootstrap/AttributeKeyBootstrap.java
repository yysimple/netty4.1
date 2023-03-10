package com.simple.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

import static io.netty.util.AttributeKey.newInstance;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 15:22
 **/
public class AttributeKeyBootstrap {
    public static void main(String[] args) {
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(
                        new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx)
                                    throws Exception {
                                Integer idValue = ctx.channel().attr(id).get();
                                // do something with the idValue
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                                        ByteBuf byteBuf) throws Exception {
                                System.out.println("Received data");
                            }
                        }
                );
        // 设置 ChannelOption，其将在 connect()或者bind()方法被调用时被设置到已经创建的Channel 上
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        bootstrap.attr(id, 123456);
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }
}
