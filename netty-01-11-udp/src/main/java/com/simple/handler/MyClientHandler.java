package com.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 21:45
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    /**
     * 接受服务端发送的内容
     *
     * @param ctx
     * @param packet
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String msg = packet.content().toString(Charset.forName("GBK"));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss -").format(new Date()) + " UDP客户端接收到消息：" + msg);
    }
}
