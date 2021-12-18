package com.simple.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 13:15
 **/
@SuppressWarnings("all")
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("本客户端链接到服务端。channelId：" + channel.id());
        System.out.println("远程地址:" + channel.remoteAddress().getHostString());
        System.out.println("远程Port:" + channel.remoteAddress().getPort());
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------- 连接完成，我要断开连接 -----------");
        System.out.println("断开链接重连" + ctx.channel().remoteAddress().toString());
        //使用过程中断线重连
        new Thread(() -> {
            try {
                new NettyClient().connect("127.0.0.1", 9999);
                System.out.println("客户端重新发起链接...");
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("重新发起链接失败");
            }
        }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收msg消息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss -").format(new Date()) + " 接收到消息：" + msg);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息，断开重连：\r\n" + cause.getMessage());
        ctx.close();
    }
}
