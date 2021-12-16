package com.simple.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 14:03
 **/
public class ServerReceiveMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        // 添加群成员
        BatchUser.USERS_YYDS.add(ctx.channel());
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " - " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().remoteAddress().toString());
        // 当有客户端退出后，从channelGroup中移除。
        BatchUser.USERS_YYDS.remove(ctx.channel());
    }

    /**
     * 这里有个小细节需要注意，因为我们在init那里配置了处理换行符的handler，如果没有识别到换行符（win上回车键）
     * 那么消息就会一直堆积在pipeline的缓存中，是刷不出去了，所以要注意发消息最后面带上 回车键
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss - ").format(new Date()) + " 接收到消息：" + msg);
        //通知客户端链消息发送成功｛不需要通过ByteBuf，可以直接发送字符串｝
        String str = "服务端收到：" + msg + "\r\n";
        // 这里就是收到消息，然后群发给通道组里面所有的成员
        BatchUser.USERS_YYDS.writeAndFlush(msg);
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
