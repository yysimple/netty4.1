package com.simple.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 15:42
 **/
public class ByteBufTest {

    /**
     * 将会成功，因为数据是共享的，对其中
     * 一个所做的更改对另外一个也是可见的
     * 所以使用 slice切片处理出来的 byteBuf 修改是有风险的，这里其实跟NIO里面的ByteBuffer差不多原理
     */
    @Test
    public void testSlice() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    /**
     * copy：这个方法之后的ByteBuf是一个全新的副本
     */
    @Test
    public void testCopy() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf copy = buf.copy(0, 15);
        System.out.println(copy.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) != copy.getByte(0);
    }

    /**
     * 其实就是修改某个索引上 对应的字节信息
     */
    @Test
    public void testGetSet() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.setByte(0, (byte) 'B');
        System.out.println((char) buf.getByte(0));
        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }

    @Test
    public void testReadWrite() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        int readBefore = buf.readerIndex();
        System.out.println((char) buf.readByte());
        int readerIndex = buf.readerIndex();
        // 因为进行了读取操作，所以这里的index是会被移动的
        assert readBefore != readerIndex;
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte) '?');
        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }

}
