package com.simple.test.out;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 16:10
 **/
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        // 写入 ByteBuf，并断言调用 readOutbound()方法将会产生数据
        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());
        // read bytes
        for (int i = 1; i < 10; i++) {
            assertEquals(i, (int) channel.readOutbound());
        }
        assertNull(channel.readOutbound());
    }
}
