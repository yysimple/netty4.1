package com.simple.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 18:37
 **/
public class ByteToCharDecoder extends ByteToMessageDecoder {
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 2) {
            // 解码，转成char传输出去
            out.add(in.readChar());
        }
    }
}
