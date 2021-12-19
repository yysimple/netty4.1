package com.simple.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 13:15
 **/
@SuppressWarnings("all")
public class ClientChannelHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("NettyClient接收到消息：" + msg + " length：" + msg.length());
        ctx.write(UUID.randomUUID().toString() + "\r\n", ctx.voidPromise());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
