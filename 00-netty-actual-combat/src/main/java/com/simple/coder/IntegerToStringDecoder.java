package com.simple.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 18:24
 **/
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

    @Override
    public void decode(ChannelHandlerContext ctx, Integer msg, List<Object>out) throws Exception {
        // 将 Integer 消息转换为它的 String 表示，并将其添加到输出的 List 中
        out.add(String.valueOf(msg));
    }
}
