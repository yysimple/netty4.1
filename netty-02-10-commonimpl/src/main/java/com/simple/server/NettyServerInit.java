package com.simple.server;

import com.simple.coder.ObjectDecoder;
import com.simple.coder.ObjectEncoder;
import com.simple.server.handler.MsgDemo01Handler;
import com.simple.server.handler.MsgDemo02Handler;
import com.simple.server.handler.MsgDemo03Handler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:12
 **/
public class NettyServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //对象传输处理
        channel.pipeline().addLast(new ObjectDecoder());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MsgDemo01Handler());
        channel.pipeline().addLast(new MsgDemo02Handler());
        channel.pipeline().addLast(new MsgDemo03Handler());
        channel.pipeline().addLast(new ObjectEncoder());
    }
}
