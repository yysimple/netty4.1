package com.simple.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 11:56
 **/
public class SocketChannelTest {

    @Test
    public void socketChannelTest() throws IOException {
        SocketChannel channel = SocketChannel.open();
        // 通过套接字去连接服务器
        channel.connect(new InetSocketAddress("127.0.0.1", 1234));
        // 这里是去配置是否需要阻塞再此
        channel.configureBlocking(false);
        // 使用buffer去接收数据
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int read = channel.read(buffer);
        while (read != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println("读取的数据：" + (char) buffer.get());
            }
            buffer.clear();
            channel.read(buffer);
        }
        channel.close();
    }
}
