package com.simple.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;

import java.nio.charset.Charset;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 14:32
 **/
public class NettyServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) {
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 流量整形；writeLimit/readLimit{0 or a limit in bytes/s}
        channel.pipeline().addLast(new GlobalTrafficShapingHandler(channel.eventLoop().parent(), 10, 10));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new ServerChannelHandler());
    }
}
