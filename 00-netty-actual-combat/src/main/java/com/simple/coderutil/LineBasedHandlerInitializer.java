package com.simple.coderutil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 功能描述: 处理由行尾符分隔的帧
 *
 * @author: WuChengXing
 * @create: 2021-12-16 10:50
 **/
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(64 * 1024));
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // Do something with the data extracted from the frame
        }
    }
}
