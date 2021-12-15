package com.simple.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 18:38
 **/
public class CharToByteEncoder extends MessageToByteEncoder<Character> {
    @Override
    public void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
        // 转成byte传出去
        out.writeChar(msg);
    }
}
