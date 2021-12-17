package com.simple.coder;

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
public class ObjectEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    public ObjectEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) {
        if (genericClass.isInstance(in)) {
            // 序列化
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
