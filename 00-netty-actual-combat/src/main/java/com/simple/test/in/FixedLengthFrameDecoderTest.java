package com.simple.test.in;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 15:43
 **/
public class FixedLengthFrameDecoderTest {

    @Test
    public void testFramesDecoded() {
        // 存储 9 个字节得 buf
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        // 复制一份数据
        ByteBuf input = buf.duplicate();
        // 创建一个EmbeddedChannel，并添加一个FixedLengthFrameDecoder，其将以 3 字节的帧长度被测试
        // 这里是传入一个ChannelHandler，然后传入得是我们指定自己逻辑的handler
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // write bytes（这里将数据写入到了ChannelPipeline的channel中）
        Assert.assertTrue(channel.writeInbound(input.retain()));
        // 标记channel为已完成写的状态
        Assert.assertTrue(channel.finish());
        // read messages 因为我们的自定义解码器是按固定长度去接受数据的，所以这里每次读取都是 3 个字节
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        // 上面释放后，这里读完了，数据没了，那么再去channel中读取就会返回null
        assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // 这里
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        System.out.println("第一次读取的数据：" + read.getByte(2));
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        System.out.println("第二次读取的数据：" + read.getByte(2));
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        System.out.println("第三次读取的数据：" + read.getByte(2));
        assertEquals(buf.readSlice(3), read);
        read.release();
        assertNull(channel.readInbound());
        buf.release();
    }

}
