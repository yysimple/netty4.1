package com.simple.client;

import com.simple.coder.ObjectDecoder;
import com.simple.coder.ObjectEncoder;
import com.simple.entity.MsgInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:12
 **/
public class NettyClientInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //对象传输处理
        channel.pipeline().addLast(new ObjectDecoder(MsgInfo.class));
        channel.pipeline().addLast(new ObjectEncoder(MsgInfo.class));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new NettyClientHandler());
    }
}
