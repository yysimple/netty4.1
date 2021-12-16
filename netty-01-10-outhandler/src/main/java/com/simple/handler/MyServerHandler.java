package com.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 21:19
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("有一客户端链接到本服务端");
        System.out.println("客户端IP:" + channel.remoteAddress().getHostString());
        System.out.println("客户端Port:" + channel.remoteAddress().getPort());
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " - " + channel.localAddress().getHostString() + "\r\n";
        //ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss -").format(new Date()) + " 服务端接收到消息：" + msg);
        //通知客户端链消息发送成功
        String str = "随机数：" + Math.random() * 10 + "\r\n";
        //ctx.writeAndFlush(str);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
