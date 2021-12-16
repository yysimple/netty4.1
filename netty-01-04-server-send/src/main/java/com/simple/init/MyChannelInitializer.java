package com.simple.init;

import com.simple.handler.ServerReceiveMsgHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:45
 **/
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        System.out.println("有一客户端链接到本服务端：");
        System.out.println("客户端地址:" + channel.remoteAddress().getHostString());
        System.out.println("客户端端口:" + channel.remoteAddress().getPort());

        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 这里记得将两个 解码器 放在前面，要不然不会生效
        channel.pipeline().addLast(new ServerReceiveMsgHandler());
    }
}
