package com.simple.coder;

import com.simple.entity.protocol.Packet;
import com.simple.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:53
 **/
public class ObjectEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) {
        byte[] data = SerializationUtil.serialize(in);
        out.writeInt(data.length + 1);
        // 添加指令
        out.writeByte(in.getCommand());
        out.writeBytes(data);
    }
}
